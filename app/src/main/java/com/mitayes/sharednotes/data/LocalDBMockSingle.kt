package com.mitayes.sharednotes.data

import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote
import java.util.UUID

class LocalDBMockSingle private constructor(): ILocalDB {
    companion object {

        @Volatile
        private var instance: LocalDBMockSingle? = null

        val noteList = ArrayList<RootNote>()

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: LocalDBMockSingle().also { instance = it }
            }
    }

    override fun init() {
        for (i in 1..6) {
            val noteUUID = UUID.randomUUID()
            val none = RootNote(
                noteUUID.toString(),
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
        noteList[position].name = note.name
        noteList[position].description = note.description
        noteList[position].shared = note.shared

        return true
    }

    override fun removeNote(position: Int): Boolean {
        if (position > noteList.size + 1) {
            return false
        }
        noteList.removeAt(position)
        return true
    }

    override fun removeNote(note: RootNote): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNoteList(): ArrayList<RootNote> = noteList
}