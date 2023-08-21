package com.mitayes.sharednotes.presentation.editRootNoteActivity

interface IEditNoteActivity {
    val noteName: String?
    val noteDescription: String?
    fun complete()
}