package com.mitayes.sharednotes.presentation.editRootNoteActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mitayes.sharednotes.R
import com.mitayes.sharednotes.databinding.ActivityEditRootNoteBinding
import com.mitayes.sharednotes.doIf
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.logD
import com.mitayes.sharednotes.presentation.mainActivity.MainActivity
import java.util.UUID

class EditNoteActivity : AppCompatActivity(), IEditNoteActivity {
    private val presenter: IEditNotePresenter by lazy { EditNotePresenter(this) }
    private val binding: ActivityEditRootNoteBinding by lazy {
        ActivityEditRootNoteBinding.inflate(
            layoutInflater
        )
    }

    override var noteName: String? = null
    override var noteDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        var editedData: RootNote? = null
        var notePosition: Int? = null
        doIf(intent.hasExtra(MainActivity.NEXT_SCREEN)) {
            editedData = intent.getParcelableExtra(MainActivity.NEXT_SCREEN)
        }

        doIf(intent.hasExtra("position")) {
            notePosition = intent.getIntExtra("position", 0)
        }

        // it the emplist is not null the it has some data and display it
        editedData?.let {
            noteName = it.name
            noteDescription = it.description

            binding.toolbar.title = getString(R.string.edit_toolbar_title)
            binding.etName.setText(noteName)
            binding.twDescription.setText(noteDescription)
        }

        binding.buttonSave.setOnClickListener {
            logD("buttonSave clicked")
            doIf(notePosition != null && editedData != null) {
                editedData?.let {
                    val newNote = RootNote(
                        it.uuid,
                        binding.etName.text.toString(),
                        binding.twDescription.text.toString(),
                        it.shared
                    )
                    notePosition?.let { p ->
                        presenter.editNote(p, newNote)
                    }
                }
            }
            doIf(!(notePosition != null && editedData != null)) {
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

    override fun complete() {
        finish()
    }
}