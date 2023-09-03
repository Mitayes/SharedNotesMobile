package com.mitayes.sharednotes.domain

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.Response

interface ISyncServerAdapter {
    fun test(): Observable<Response>
    fun addNote(note: RootNote) : Completable
    fun editNote(note: RootNote) : Completable
    fun removeNote(note: RootNote) : Completable
    fun syncNotes(notesList: MutableList<RootNote>): Completable
}