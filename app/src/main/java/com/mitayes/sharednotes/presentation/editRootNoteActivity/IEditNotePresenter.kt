package com.mitayes.sharednotes.presentation.editRootNoteActivity

import com.mitayes.sharednotes.domain.RootNote

interface IEditNotePresenter {
    fun saveNewNote(newNote: RootNote)
    fun editNote(position: Int, note: RootNote)
}