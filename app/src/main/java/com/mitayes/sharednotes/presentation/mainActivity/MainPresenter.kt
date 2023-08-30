package com.mitayes.sharednotes.presentation.mainActivity

import com.mitayes.sharednotes.data.LocalDBMockSingle
import com.mitayes.sharednotes.data.sqlite.LocalDBSQLite
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class MainPresenter(
    private val view: IMainView,
) : IMainPresenter {
    private val localDB: ILocalDB = LocalDBMockSingle.getInstance()
    private val sqliteDB: LocalDBSQLite = LocalDBSQLite()
    private val bag = CompositeDisposable()

    init {
        localDB.init()

    }

    override fun loadNoteList() {
        val noteList = localDB.getNoteList()
        for (item in noteList) {
            view.adapter.addNote(item)
        }
        sqliteDB.getNoteList(true)
            .map { list ->
                list.map {
                    it.toRootNote()
                }
            }
            .bindSubscribe({ data ->
//                val newList = mutableListOf<RootNote>()
//                data.forEach {
//                    newList.add(it.toRootNote())
//                }
                view.adapter.addNotes(data.toList())
            },
                {
                    val s = 3
                }
            )
    }

    override fun getNote(position: Int): RootNote {
        return view.adapter.getNote(position)
    }

    override fun editNote(position: Int, note: RootNote): Boolean {
        if (localDB.editNote(position, note)) {
            view.adapter.editNote(position, note)
            return true
        }
        return false
    }

    override fun removeNote(position: Int): Boolean {
        if (localDB.removeNote(position)) {
            view.adapter.removeNote(position)
            return true
        }
        return false
    }

    override fun reloadNotes() {
        view.adapter.noteClear()
        for (note in localDB.getNoteList()) {
            view.adapter.addNote(note)
        }
    }

    override fun syncNotes() {
        TODO("Not yet implemented")
    }

    private fun<T> Single<T>.bindSubscribe(onSuccess: Consumer<in T>, onError: Consumer<in Throwable>){
        bag.add(this.subscribe(onSuccess, onError))
    }
}
