package com.mitayes.sharednotes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mitayes.sharednotes.domain.RootNote

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rootNoteRecyclerViewList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RootNotesAdapter(fillList())
    }

    private fun fillList(): ArrayList<RootNote> {
        val rootNotes = ArrayList<RootNote>()

        for (i in 1..15) {
            val rootNone = RootNote(
                "001",
                "Заметка ${i}",
                "Описание для заметки ${i}",
                0
            )
            rootNotes.add(rootNone)
        }
        return rootNotes
    }
    private fun isContactsPermissionExists(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

}