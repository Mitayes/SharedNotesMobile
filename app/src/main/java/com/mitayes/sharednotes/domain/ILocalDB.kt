package com.mitayes.sharednotes.domain

import io.reactivex.Completable
import io.reactivex.Single

interface ILocalDB {
    fun init()
    fun addNote(note: RootNote) : Completable
    fun editNote(note: RootNote) : Completable
    fun removeNote(note: RootNote) : Completable
    fun getNoteList(): Single<MutableList<RootNote>>
    fun getNote(uuid: String): Single<MutableList<RootNote>>
}