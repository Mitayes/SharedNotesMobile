package com.mitayes.sharednotes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitayes.sharednotes.databinding.ActivityMainBinding
import com.mitayes.sharednotes.domain.RootNote

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = NoteAdapter()
    private var editNoteLauncher: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
//        val recyclerView: RecyclerView = findViewById(R.id.rootNoteRecyclerViewList)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = NoteAdapter()
        fillList(adapter)
//        editNoteLauncher = registerForActivityResult(ActivityResultContract.StartActivityForResult())
    }

    private fun init() = with(binding){
        rootNoteRecyclerViewList.layoutManager = LinearLayoutManager(this@MainActivity)
        rootNoteRecyclerViewList.adapter = adapter
        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity, "You clicked on $position", Toast.LENGTH_SHORT).show()
            }

        })
        buttonNewRootNote.setOnClickListener {
            val intent = Intent(this@MainActivity, EditRootNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fillList(adapter: NoteAdapter) {
//        val rootNotes = ArrayList<RootNote>()

        for (i in 1..15) {
            val rootNone = RootNote(
                "001",
                "Заметка ${i}",
                "Описание для заметки ${i}",
                i % 2 == 0
            )
            adapter.addNote(rootNone)
//            rootNotes.add(rootNone)
        }
//        return rootNotes
    }
    private fun isContactsPermissionExists(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

}