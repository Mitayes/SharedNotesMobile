package com.mitayes.sharednotes.domain.sqlite

import com.mitayes.sharednotes.data.sqlite.AppDatabase
import com.mitayes.sharednotes.data.sqlite.RootNotesDao
import com.mitayes.sharednotes.presentation.MyApplication

class RootNoteRepository {
    private val dao: RootNotesDao by lazy { AppDatabase.getInstance(MyApplication.appContext).rootNotesDao() }
    fun getAllNotes() = dao.getAllRootNotes()
    fun getNote(uuid: String) = dao.getRootNote(uuid)
    fun insertNewRootNote(rootNotesDBEntity: RootNotesDBEntity) = dao.insertRootNote(rootNotesDBEntity)
    fun editRootNote(rootNotesDBEntity: RootNotesDBEntity) = dao.updateRootNote(rootNotesDBEntity)
    fun removeRootNote(note: RootNotesDBEntity) = dao.deleteRootNote(note)
}