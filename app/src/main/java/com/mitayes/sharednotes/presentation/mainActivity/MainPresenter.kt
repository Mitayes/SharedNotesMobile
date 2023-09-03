package com.mitayes.sharednotes.presentation.mainActivity

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.mitayes.sharednotes.data.api.APISyncAdapter
import com.mitayes.sharednotes.doIf
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.ISyncServerAdapter
import com.mitayes.sharednotes.domain.sqlite.LocalDBSQLite
import com.mitayes.sharednotes.logE
import com.mitayes.sharednotes.presentation.MyApplication
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class MainPresenter(
    private val view: IMainView,
) : IMainPresenter {
    private val localDB: ILocalDB by lazy { LocalDBSQLite() }
    private val syncAdapter: ISyncServerAdapter by lazy { APISyncAdapter() }
    private val bag = CompositeDisposable()

    init {
        localDB.init()
    }

    // TODO Переписать метод: неплохо было бы сравнить что есть сейчас и что получили из базы и синхронизировать
    override fun reloadNotes() {
        view.adapter.noteClear()

        localDB.getNoteList()
            .bindSubscribe({ data ->
                view.adapter.addNotes(data.toList())
            },
                {
                    logE(it.stackTraceToString())
                }
            )
    }

    private fun <T> Single<T>.bindSubscribe(
        onSuccess: Consumer<in T>,
        onError: Consumer<in Throwable>
    ) {
        bag.add(this.subscribe(onSuccess, onError))
    }

    override fun removeNote(position: Int, context: Context) {
        val note = view.adapter.getNote(position)
        AlertDialog.Builder(context)
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить заметку: \"${note.name}\"?")
            .setPositiveButton("да") { _, _ ->
                bag.add(localDB.removeNote(note)
                    .subscribe(
                        {
                            view.adapter.removeNote(position)
                            bag.add(syncAdapter.removeNote(note)
                                .subscribe(
                                    {
                                        // Пометить, что заметка синхронизировалась
                                    },
                                    {
                                        logE(it.stackTraceToString())
                                    }
                                ))
                        },
                        {
                            logE(it.stackTraceToString())
                        }
                    )
                )
            }
            .setNegativeButton("нет") { _, _ -> }
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
                show()
            }
    }

    override fun cloudSyncNotes() {
        bag.add(
            syncAdapter.test().subscribe(
                { response ->
                    doIf(response.isSuccessful) {
                        response.body?.let { body ->
                            Toast.makeText(
                                MyApplication.appContext,
                                "Заметки синхронизированы",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                {
                    logE(it.stackTraceToString())
                    Toast.makeText(
                        MyApplication.appContext,
                        "Ошибка синхронизации",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        )
    }

    override fun onDestroy() {
        bag.clear()
    }
}
