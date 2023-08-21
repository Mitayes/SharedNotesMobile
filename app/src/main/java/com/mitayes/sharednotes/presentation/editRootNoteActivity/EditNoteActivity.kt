package com.mitayes.sharednotes.presentation.editRootNoteActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.mitayes.sharednotes.databinding.ActivityEditRootNoteBinding
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.presentation.mainActivity.MainActivity
import java.util.UUID

class EditNoteActivity : AppCompatActivity(), IEditNoteActivity {
    private val presenter: IEditNotePresenter = EditNotePresenter(this)
    lateinit var binding: ActivityEditRootNoteBinding

    override var noteName: String ?= null
    override var noteDescription: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRootNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        var editedData : RootNote?=null
        var notePosition : Int?=null

        // checking if the intent has extra
        if(intent.hasExtra(MainActivity.NEXT_SCREEN)){
            // get the Serializable data model class with the details in it
            editedData = intent.getSerializableExtra(MainActivity.NEXT_SCREEN) as RootNote
        }
        if(intent.hasExtra("position")){
            // get the Serializable data model class with the details in it
            notePosition = intent.getSerializableExtra("position") as Int
        }
        // it the emplist is not null the it has some data and display it
        if(editedData != null){
            noteName = editedData.name
            noteDescription = editedData.description

            binding.toolbar.title = "Редактирование заметки"
            binding.etName.setText(noteName)
            binding.twDescription.setText(noteDescription)
        }

        binding.buttonSave.setOnClickListener {
            Log.d("CUSTOM_LOG", "buttonSave clicked")
            if (notePosition!= null && editedData != null){
                val newNote = RootNote(
                    editedData.uuid,
                    binding.etName.text.toString(),
                    binding.twDescription.text.toString(),
                    editedData.shared
                )
                presenter.editNote(notePosition, newNote)
            } else {
                val newNote = RootNote(
                    UUID.randomUUID().toString(),
                    binding.etName.text.toString(),
                    binding.twDescription.text.toString(),
                    false
                )
                presenter.saveNewNote(newNote)
            }
        }
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("TAG", "long tap")
        return super.onKeyLongPress(keyCode, event)
    }

    override fun complete() {
        finish()
    }
}