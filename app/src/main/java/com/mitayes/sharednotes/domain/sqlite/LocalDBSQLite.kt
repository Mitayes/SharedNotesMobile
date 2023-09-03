package com.mitayes.sharednotes.domain.sqlite

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
        return repository.insertNewRootNote(RootNotesDBEntity(note))
            .onIo()
            .onUi()
    }
    override fun editNote(note: RootNote): Completable {
        return repository.editRootNote(RootNotesDBEntity(note))
            .onIo()
            .onUi()
    }
    override fun removeNote(note: RootNote): Completable {
        return repository.removeRootNote(RootNotesDBEntity(note))
            .onIo()
            .onUi()
    }
    override fun getNote(uuid: String) : Single<MutableList<RootNote>> {
        return repository.getNote(uuid)
            .onIo()
            .map { list ->
                list.map {
                    RootNote(it)
                }.toMutableList()
            }
    }
    override fun getNoteList(): Single<MutableList<RootNote>> {
        return repository.getAllNotes()
            .onIo()
            .map { list ->
                list.map {
                    RootNote(it)
                }.toMutableList()
            }
    }
    override fun getNoteListForSync(): Single<MutableList<RootNote>> {
        return repository.getAllNotesForSync()
            .onIo()
            .map { list ->
                list.map {
                    RootNote(it)
                }.toMutableList()
            }
    }
}
    