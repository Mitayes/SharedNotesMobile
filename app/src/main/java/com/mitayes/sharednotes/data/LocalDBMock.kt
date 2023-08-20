package com.mitayes.sharednotes.data

import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote

class LocalDBMock : ILocalDB {
    private val noteList = ArrayList<RootNote>()
    override fun init() {
        for (i in 1..6) {
            val none = RootNote(
                "001",
                "Заметка $i",
                "Описание для заметки $i",
                i % 2 == 0
            )
            addNote(none)
        }
    }

    override fun addNote(note: RootNote): Boolean {
        noteList.add(note)
        return true
    }

    override fun editNote(position: Int, note: RootNote): Boolean {
        if (position > noteList.size + 1) {
            return false
        }
        noteList[position] = note
        return true
    }

    override fun removeNote(position: Int): Boolean {
        if (position > noteList.size + 1) {
            return false
        }
        noteList.removeAt(position)
        return true
    }

    override fun getNoteList(): ArrayList<RootNote> = noteList

}