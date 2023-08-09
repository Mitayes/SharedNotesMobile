package com.mitayes.sharednotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import com.mitayes.sharednotes.domain.RootNote


class RootNotesAdapter (private val dataSet: ArrayList<RootNote>) :
    RecyclerView.Adapter<RootNotesAdapter.RootNotesViewHolder>() {
    private var onClickListener: OnClickListener? = null

    class RootNotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.name)
        val textViewDescription: TextView = itemView.findViewById(R.id.description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootNotesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.root_note_item, parent, false)

        return RootNotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RootNotesViewHolder, position: Int) {
        val item = dataSet[position]
        holder.textViewName.text = item.name
        holder.textViewDescription.text = item.description

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: RootNote)
    }

}
