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
import com.mitayes.sharednotes.domain.sqlite.LocalDBSQLite
import com.mitayes.sharednotes.databinding.RootNoteItemBinding
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.logE
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.Date

typealias ClickIconAction = ((Int, AppCompatActivity) -> Unit)
typealias LongClickItemAction = ((Int) -> Unit)

class NoteAdapter(private val context: MainActivity) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private val noteList = mutableListOf<RootNote>()
    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener? = null
    var iconSharedClickAction: ClickIconAction? = null
    var itemLongClickAction: LongClickItemAction? = null

    private val localDB: LocalDBSQLite = LocalDBSQLite()
    private val bag = CompositeDisposable()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RootNoteItemBinding.bind(itemView)
        @SuppressLint("SimpleDateFormat")
        fun bind(rootNote: RootNote) = with(binding) {
            name.text = rootNote.name
            description.text = rootNote.description
            if (rootNote.shared) {
                iconShared.setImageResource(R.drawable.baseline_share_true)
            } else {
                iconShared.setImageResource(R.drawable.outline_share_false)
            }
            updateDate.text = rootNote.updateDate?.let {
                SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(it)
            }

            iconShared.setOnClickListener {
                iconSharedClickAction?.invoke(adapterPosition, context)
            }

            itemView.setOnLongClickListener {
                itemLongClickAction?.invoke(adapterPosition)
                return@setOnLongClickListener true
            }
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
        iconSharedClickAction = { note: Int, context: AppCompatActivity ->
            noteList[note].shared = !noteList[note].shared
            noteList[note].updateDate = Date()

            bag.add(localDB.editNote(noteList[note])
                .subscribe(
                    {
                        notifyItemChanged(note)

                        if (noteList[note].shared) {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, noteList[note].uuid)
                                type = "text/plain"
                            }
                            startActivity(context, sendIntent, null)
                        }
                    },
                    {
                        noteList[note].shared = !noteList[note].shared
                        logE(it.stackTraceToString())
                    }
                )
            )
        }
    }

    override fun getItemCount(): Int = noteList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addNotes(list: List<RootNote>) {
        list.forEach {
            noteList.add(it)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeNote(position: Int) {
        noteList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getNote(position: Int): RootNote {
        return noteList[position]
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

    fun onDestroy() {
        bag.clear()
    }

    interface OnClickListener {
        fun onClick(position: Int, model: RootNote)
    }

    interface OnLongClickListener {
        fun onLongClick(position: Int)
    }
}