package com.mitayes.sharednotes.presentation.mainActivity

import com.mitayes.sharednotes.data.LocalDBMockSingle
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote

class MainPresenter(
    private val view: IMainView,
) : IMainPresenter {
    private val localDB: ILocalDB = LocalDBMockSingle.getInstance()

    init { localDB.init() }

    override fun loadNoteList() {
        val noteList = localDB.getNoteList()
        for (item in noteList){
            view.adapter.addNote(item)
        }
    }

    override fun getNote(position: Int): RootNote {
        return view.adapter.getNote(position)
    }

    override fun addNote(note: RootNote) : Boolean {
        if (localDB.addNote(note)){
            view.adapter.addNote(note)
            return true
        }
        return false
    }

    override fun editNote(position: Int, note: RootNote) : Boolean {
        if (localDB.editNote(position, note)){
            view.adapter.editNote(position, note)
            return true
        }
        return false
    }

    override fun removeNote(position: Int) : Boolean {
        if (localDB.removeNote(position)){
            view.adapter.removeNote(position)
            return true
        }
        return false
    }

    override fun reloadNotes() {
        view.adapter.noteClear()
        for (note in localDB.getNoteList()){
            view.adapter.addNote(note)
        }
    }
    override fun syncNotes() {
        TODO("Not yet implemented")
    }
}