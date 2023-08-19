package com.mitayes.sharednotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mitayes.sharednotes.databinding.ActivityEditRootNoteBinding
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.presentation.MainActivity

class EditRootNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditRootNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRootNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        var noteList : RootNote?=null

        // checking if the intent has extra
        if(intent.hasExtra(MainActivity.NEXT_SCREEN)){
            // get the Serializable data model class with the details in it
            noteList = intent.getSerializableExtra(MainActivity.NEXT_SCREEN) as RootNote
        }
        // it the emplist is not null the it has some data and display it
        if(noteList != null){
            binding.toolbar.title = "Редактирование заметки"
            binding.etName.setText(noteList.name)
            binding.twDescription.setText(noteList.description)
        }
    }
}