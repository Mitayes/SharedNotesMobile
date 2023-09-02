package com.mitayes.sharednotes.presentation.mainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitayes.sharednotes.databinding.ActivityMainBinding
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.presentation.editRootNoteActivity.EditNoteActivity


class MainActivity : AppCompatActivity(), IMainView {
    override val adapter = NoteAdapter(this)

    private val presenter: IMainPresenter by lazy { MainPresenter(this) }
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        presenter.reloadNotes()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        adapter.onDestroy()
        super.onDestroy()
    }

    private fun init() {
        with(binding.rootNoteRecyclerViewList) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            addItemDecoration(PaddingDecorator())
        }

        with(adapter) {
            // Настраиваем действие при быстром нажатии на заметку
            setOnClickListener(object : NoteAdapter.OnClickListener {
                override fun onClick(position: Int, model: RootNote) {
                    val intent = Intent(this@MainActivity, EditNoteActivity::class.java).apply {
                        putExtra(NEXT_SCREEN, model)
                        putExtra("position", position)
                    }
                    startActivity(intent)
                }
            })

            // Настраиваем действие при длительном нажатии на заметку
            setOnLongClickListener(object : NoteAdapter.OnLongClickListener {
                override fun onLongClick(position: Int) {
                    presenter.removeNote(position, this@MainActivity)
                }
            })
        }
        // Настраиваем действие при нажатии кнопку добавить заметку
        binding.buttonNewRootNote.setOnClickListener {
            val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val NEXT_SCREEN = "details_screen"
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
