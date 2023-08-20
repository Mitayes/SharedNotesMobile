package com.mitayes.sharednotes.presentation

import com.mitayes.sharednotes.domain.RootNote

interface IMainPresenter {
    fun addNote(note: RootNote) : Boolean
    fun editNote(position: Int, note: RootNote) : Boolean
    fun removeNote(position: Int) : Boolean
    fun loadNoteList()
    fun syncNotes()
}