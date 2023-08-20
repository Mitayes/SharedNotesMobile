package com.mitayes.sharednotes.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitayes.sharednotes.databinding.ActivityMainBinding
import com.mitayes.sharednotes.domain.RootNote

class MainActivity : AppCompatActivity(), IMainView {
    override val adapter = NoteAdapter()

    private val presenter: IMainPresenter = MainPresenter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        presenter.loadNoteList()
    }

    private fun init() = with(binding){
        rootNoteRecyclerViewList.layoutManager = LinearLayoutManager(this@MainActivity)
        rootNoteRecyclerViewList.adapter = adapter
        adapter.setOnClickListener(object : NoteAdapter.OnClickListener {
            override fun onClick(position: Int, model: RootNote) {
                val intent = Intent(this@MainActivity, EditRootNoteActivity::class.java)
                intent.putExtra(NEXT_SCREEN, model)
                startActivity(intent)
            }
        })

        buttonNewRootNote.setOnClickListener {
            val intent = Intent(this@MainActivity, EditRootNoteActivity::class.java)
            startActivity(intent)
        }
    }

companion object{
    const val NEXT_SCREEN="details_screen"
}

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
}