package com.mitayes.sharednotes.presentation.editRootNoteActivity

import com.mitayes.sharednotes.data.LocalDBMockSingle
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote

class EditNotePresenter(
    private val view: IEditNoteActivity,
): IEditNotePresenter {
    private val localDB: ILocalDB = LocalDBMockSingle.getInstance()
    override fun saveNewNote(newNote: RootNote) {
        localDB.addNote(newNote)
        view.complete()
    }

    override fun editNote(position: Int, note: RootNote) {
        localDB.editNote(position, note)
        view.complete()
    }

}