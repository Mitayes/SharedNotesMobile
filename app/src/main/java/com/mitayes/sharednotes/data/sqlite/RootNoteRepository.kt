package com.mitayes.sharednotes.data.sqlite

import com.mitayes.sharednotes.domain.AppDatabase
import com.mitayes.sharednotes.domain.RootNotesDao
import com.mitayes.sharednotes.presentation.MyApplication

class RootNoteRepository {
    private val dao: RootNotesDao by lazy { AppDatabase.getInstance(MyApplication.appContext).rootNotesDao() }
    fun getAllNotes() = dao.getAllRootNotes()
    fun insertNewRootNote(rootNotesDBEntity: RootNotesDBEntity) = dao.insertRootNote(rootNotesDBEntity)
    fun editRootNote(rootNotesDBEntity: RootNotesDBEntity) = dao.updateRootNote(rootNotesDBEntity)
    fun removeRootNote(note: RootNotesDBEntity) = dao.deleteRootNote(note)
}