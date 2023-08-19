package com.mitayes.sharednotes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitayes.sharednotes.databinding.RootNoteItemBinding
import com.mitayes.sharednotes.domain.RootNote

typealias ClickTextAction = ((Int) -> Unit)

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private val noteList = ArrayList<RootNote>()
    private var onClickListener: OnClickListener? = null

    var iconSharedClickAction: ClickTextAction? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RootNoteItemBinding.bind(itemView)
    fun bind(rootNote: RootNote) = with(binding){
            name.text = rootNote.name
            description.text = rootNote.description
            if (rootNote.shared) {
                iconShared.setImageResource(R.drawable.baseline_share_true)
            } else {
                iconShared.setImageResource(R.drawable.outline_share_false)
            }

            iconShared.setOnClickListener {
                iconSharedClickAction?.invoke(adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Тут мы создаём элемент RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.root_note_item, parent, false)
//        iconSharedClickAction = {
//            noteList[it].shared = !noteList[it].shared
//            notifyItemChanged(it)
//        }
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Тут мы заполняем шаблон элемента RecyclerView
        holder.bind(noteList[position])

        // Назначаем clickListener для тапа по элементу RecyclerView
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, noteList[position])
        }
        // Назначаем clickListener для тапа по iconShared
        iconSharedClickAction = {
            noteList[it].shared = !noteList[it].shared
            notifyItemChanged(it)
        }
    }

    override fun getItemCount(): Int = noteList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(rootNote: RootNote) {
        noteList.add(rootNote)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun editNote(position: Int, rootNote: RootNote) {
        noteList[position] = rootNote
        notifyDataSetChanged()
    }

    fun getNote(position: Int) : RootNote {
        return noteList[position]
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: RootNote)
    }
}

