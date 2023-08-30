package com.mitayes.sharednotes.domain

interface ILocalDB {
    fun init()
    fun addNote(note: RootNote) : Boolean
    fun editNote(position: Int, note: RootNote) : Boolean
    fun removeNote(position: Int) : Boolean
    fun removeNote(note: RootNote) : Boolean
    fun getNoteList(): ArrayList<RootNote>
}