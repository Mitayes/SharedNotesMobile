package com.mitayes.sharednotes.presentation.mainActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.mitayes.sharednotes.R
import com.mitayes.sharednotes.data.sqlite.LocalDBSQLite
import com.mitayes.sharednotes.databinding.RootNoteItemBinding
import com.mitayes.sharednotes.domain.RootNote

typealias ClickIconAction = ((Int, AppCompatActivity) -> Unit)
typealias LongClickItemAction = ((Int) -> Unit)

class NoteAdapter(private val context: MainActivity) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private val noteList = mutableListOf<RootNote>()
    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener? = null
    var iconSharedClickAction: ClickIconAction? = null
    var itemLongClickAction: LongClickItemAction? = null

    private val sqliteDB: LocalDBSQLite = LocalDBSQLite()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RootNoteItemBinding.bind(itemView)
    fun bind(rootNote: RootNote) = with(binding){
            name.text = rootNote.name
            description.text = rootNote.description
            if (rootNote.shared) {
                iconShared.setImageResource(R.drawable.baseline_share_true)
            } else {
                iconShared.setImageResource(R.drawable.outline_share_false)
            }

            iconShared.setOnClickListener {
                iconSharedClickAction?.invoke(adapterPosition, context)
            }

            itemView.setOnLongClickListener {
                itemLongClickAction?.invoke(adapterPosition)
                return@setOnLongClickListener true
            }

//            itemView.setOnLongClickListener {
////                Toast.makeText(it.context, "Position is $adapterPosition", Toast.LENGTH_SHORT).show()
//                context.presenter.removeNote(adapterPosition)
//                return@setOnLongClickListener true
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Тут мы создаём элемент RecyclerView
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.root_note_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Тут мы заполняем шаблон элемента RecyclerView
        holder.bind(noteList[position])

        // Назначаем clickListener для тапа по элементу RecyclerView
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, noteList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongClickListener?.onLongClick(position)
            return@setOnLongClickListener true
        }
        // Назначаем clickListener для тапа по iconShared
        iconSharedClickAction = { it: Int, context: AppCompatActivity ->
            noteList[it].shared = !noteList[it].shared
            notifyItemChanged(it)

            if (noteList[it].shared){
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, noteList[it].uuid)
                    type = "text/plain"
                }
                startActivity(context, sendIntent, null)
            }
        }
    }

    override fun getItemCount(): Int = noteList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(rootNote: RootNote) {
        noteList.add(rootNote)
        notifyDataSetChanged()

        sqliteDB.addNote(rootNote)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNotes(list: List<RootNote>) {
        list.forEach {
            noteList.add(it)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun editNote(position: Int, rootNote: RootNote) {
        noteList[position].name = rootNote.name
        noteList[position].description = rootNote.description
        noteList[position].shared = rootNote.shared
        notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeNote(position: Int) {
        noteList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getNote(position: Int) : RootNote {
        return noteList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadNotes(noteList: ArrayList<RootNote>) {
        noteList.clear()
        for(note in noteList){
            noteList.add(note)
        }
        notifyDataSetChanged()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    fun setOnLongClickListener(onLongClickListener: OnLongClickListener) {
        this.onLongClickListener = onLongClickListener
    }

    fun noteClear() {
        noteList.clear()
    }

    interface OnClickListener {
        fun onClick(position: Int, model: RootNote)
    }
    interface OnLongClickListener {
        fun onLongClick(position: Int)
    }

}