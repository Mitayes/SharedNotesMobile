package com.mitayes.sharednotes.presentation.mainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitayes.sharednotes.databinding.ActivityMainBinding
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.presentation.editRootNoteActivity.EditNoteActivity

class MainActivity : AppCompatActivity(), IMainView {
    override val adapter = NoteAdapter()

    private val presenter: IMainPresenter = MainPresenter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("CUSTOM_LOG", "MainActivity created")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        presenter.loadNoteList()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        presenter.reloadNotes()
        adapter.notifyDataSetChanged()
    }
    private fun init() = with(binding){
        rootNoteRecyclerViewList.layoutManager = LinearLayoutManager(this@MainActivity)
        rootNoteRecyclerViewList.adapter = adapter
        adapter.setOnClickListener(object : NoteAdapter.OnClickListener {
            override fun onClick(position: Int, model: RootNote) {
                val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
                intent.putExtra(NEXT_SCREEN, model)
                intent.putExtra("position", position)
                startActivity(intent)
            }
        })

        buttonNewRootNote.setOnClickListener {
            val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
            startActivity(intent)
        }
    }

companion object{
    const val NEXT_SCREEN="details_screen"
}

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}