package com.mitayes.sharednotes.data.sqlite

import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.onIo
import com.mitayes.sharednotes.onUi
import io.reactivex.Completable
import io.reactivex.Single

class LocalDBSQLite : ILocalDB {
    private val repository: RootNoteRepository by lazy { RootNoteRepository() }
    override fun init() { }
    override fun addNote(note: RootNote): Completable {
        return repository.insertNewRootNote(note.toRootNotesDbEntity())
            .onIo()
            .onUi()
    }
    override fun editNote(note: RootNote): Completable {
        return repository.editRootNote(note.toRootNotesDbEntity())
            .onIo()
            .onUi()
    }
    override fun removeNote(note: RootNote): Completable {
        return repository.removeRootNote(note.toRootNotesDbEntity())
            .onIo()
            .onUi()
    }
    override fun getNote(uuid: String) : Single<MutableList<RootNote>> {
        return repository.getNote(uuid)
            .onIo()
            .map { list ->
                list.map {
                    it.toRootNote()
                }.toMutableList()
            }
    }
    override fun getNoteList(): Single<MutableList<RootNote>> {
        return repository.getAllNotes()
            .onIo()
            .map { list ->
                list.map {
                    it.toRootNote()
                }.toMutableList()
            }
    }
}
    