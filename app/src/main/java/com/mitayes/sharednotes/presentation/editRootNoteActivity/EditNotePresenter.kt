package com.mitayes.sharednotes.presentation.editRootNoteActivity

import com.mitayes.sharednotes.data.LocalDBMockSingle
import com.mitayes.sharednotes.doIf
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote

class EditNotePresenter(
    private val view: IEditNoteActivity,
): IEditNotePresenter {
    private val localDB: ILocalDB by lazy { LocalDBMockSingle.getInstance() }
    override fun saveNewNote(newNote: RootNote) {
        doIf(newNote.name.isNotBlank() || newNote.description.isNotBlank()){
            localDB.addNote(newNote)
        }
        view.complete()
    }

    override fun editNote(position: Int, note: RootNote) {
        localDB.editNote(position, note)
        view.complete()
    }

}