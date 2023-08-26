package com.mitayes.sharednotes.presentation.mainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mitayes.sharednotes.databinding.ActivityMainBinding
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.presentation.editRootNoteActivity.EditNoteActivity


class MainActivity : AppCompatActivity(), IMainView {
    override val adapter = NoteAdapter(this)

    val presenter: IMainPresenter = MainPresenter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        rootNoteRecyclerViewList.addItemDecoration(object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                parent.adapter?.let{
                    if (parent.getChildAdapterPosition(view) != it.itemCount - 1){
                        outRect.bottom = dpToPx(10F).toInt()
                    }
                }
            }
        })

        // Настраиваем действие при быстром нажатии на заметку
        adapter.setOnClickListener(object : NoteAdapter.OnClickListener {
            override fun onClick(position: Int, model: RootNote) {
                val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
                intent.putExtra(NEXT_SCREEN, model)
                intent.putExtra("position", position)
                startActivity(intent)
            }
        })

        // Настраиваем действие при длительном нажатии на заметку
        adapter.setOnLongClickListener(object : NoteAdapter.OnLongClickListener{
            override fun onLongClick(position: Int) {
                showAlertDialogAndDeleteNote(position)
            }
        })

        // Настраиваем действие при нажатии кнопку добавить заметку
        buttonNewRootNote.setOnClickListener {
            val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
            startActivity(intent)
        }

    }


    fun dpToPx(px: Float): Float {
        return px * resources.displayMetrics.density
    }

    private fun showAlertDialogAndDeleteNote(position: Int) {
        val note = presenter.getNote(position)
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("Удаление")
        alertDialog.setMessage("Вы действительно хотите удалить заметку: \"${note.name}\"?")
        alertDialog.setPositiveButton(
            "да"
        ) { _, _ ->
            presenter.removeNote(position)
        }
        alertDialog.setNegativeButton(
            "нет"
        ) { _, _ -> }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
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

