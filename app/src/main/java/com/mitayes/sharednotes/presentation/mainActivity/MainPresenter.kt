package com.mitayes.sharednotes.presentation.mainActivity

import android.app.AlertDialog
import android.content.Context
import com.mitayes.sharednotes.data.api.APISyncAdapter
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.ISyncServerAdapter
import com.mitayes.sharednotes.domain.sqlite.LocalDBSQLite
import com.mitayes.sharednotes.logE
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
                bag.add(
                    localDB.removeNote(note)
                        .andThen(syncAdapter.removeNote(note))
                        .andThen(localDB.updateSyncFlag(note.uuid, 1))
                        .doOnError {
                            logE(it.stackTraceToString())
                        }
                        .subscribe {
                            view.adapter.removeNote(position)
                        }
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

    }

    override fun onDestroy() {
        bag.clear()
    }
}
