package com.mitayes.sharednotes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.mitayes.sharednotes.databinding.RootNoteItemBinding
import com.mitayes.sharednotes.domain.RootNote

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private lateinit var onClickListener: OnItemClickListener
    private val noteList = ArrayList<RootNote>()
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        onClickListener = listener
    }
    class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val binding = RootNoteItemBinding.bind(itemView)
        fun bind(rootNote: RootNote) = with(binding){
            // https://youtu.be/4pevVON0v-U?t=1506
            name.text = rootNote.name
            description.text = rootNote.description
            if (rootNote.shared) {
                iconShared.setImageResource(R.drawable.baseline_share_true)
            } else {
                iconShared.setImageResource(R.drawable.outline_share_false)
            }
//            itemView.setOnClickListener {
//                if (ViewCompat.hasOnClickListeners(itemView) && onClickListener)
//            }
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Тут мы создаём элемент RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.root_note_item, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Тут мы заполняем шаблон элемента RecyclerView
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(rootNote: RootNote) {
        noteList.add(rootNote)
        notifyDataSetChanged()
    }

}