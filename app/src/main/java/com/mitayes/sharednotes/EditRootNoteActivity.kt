package com.mitayes.sharednotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mitayes.sharednotes.databinding.ActivityEditRootNoteBinding

class EditRootNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditRootNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRootNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}