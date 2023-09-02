package com.mitayes.sharednotes.presentation.mainActivity

import android.content.Context

interface IMainPresenter {
    fun removeNote(position: Int, context: Context)
    fun reloadNotes()
    fun cloudSyncNotes()
    fun onDestroy()
}

interface IMainView {
    val adapter: NoteAdapter
}
