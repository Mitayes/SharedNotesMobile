package com.mitayes.sharednotes.presentation

import com.mitayes.sharednotes.data.LocalDBMock
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote

class MainPresenter(
    private val view: IMainView,
) : IMainPresenter {
    private val localDB: ILocalDB = LocalDBMock()

    init {
        localDB.init()
    }
    override fun addNote(note: RootNote) : Boolean {
        return localDB.addNote(note)
    }

    override fun editNote(position: Int, note: RootNote) : Boolean {
        return localDB.editNote(position, note)
    }

    override fun removeNote(position: Int) : Boolean {
        return localDB.removeNote(position)
    }

    override fun loadNoteList() {
        val noteList = localDB.getNoteList()
        for (item in noteList){
            view.adapter.addNote(item)
        }
    }

    override fun syncNotes() {
        TODO("Not yet implemented")
    }
}