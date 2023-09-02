package com.mitayes.sharednotes.presentation.mainActivity

import android.content.Context

interface IMainPresenter {
    fun removeNote(position: Int, context: Context)
    fun reloadNotes()
    fun cloudSyncNotes()
}

interface IMainView {
    val adapter: NoteAdapter
}
