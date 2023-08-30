package com.mitayes.sharednotes.data.sqlite

import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.onIo
import com.mitayes.sharednotes.onUi
import io.reactivex.Single

class LocalDBSQLite: ILocalDB {
    private val repository: RootNoteRepository by lazy { RootNoteRepository() }
    override fun init() {

    }

    override fun addNote(note: RootNote): Boolean {
        repository.insertNewRootNote(note.toRootNotesDbEntity())
            .onIo()
            .subscribe()
        return true
    }

    override fun editNote(position: Int, note: RootNote): Boolean {
        repository.editRootNote(note.toRootNotesDbEntity())
            .onIo()
            .subscribe()
        return true
    }

    override fun removeNote(position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeNote(note: RootNote): Boolean {
        repository.removeRootNote(note.toRootNotesDbEntity())
            .onIo()
            .subscribe()
        return true
    }

    override fun getNoteList(): ArrayList<RootNote> {
        return ArrayList()
    }

    fun getNoteList(v: Boolean = true): Single<MutableList<RootNoteTuple>> {
        return repository.getAllNotes()
            .onIo()
            .onUi()
    }
}
    