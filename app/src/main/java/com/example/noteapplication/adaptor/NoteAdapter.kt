package com.example.noteapplication.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.R
import com.example.noteapplication.room.Note

class NoteAdapter(val listener: OnClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes: MutableList<Note> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.descriptionTextView.text = note.descriptions
        holder.idTextView.text = note.id.toString()

        holder.itemView.setOnClickListener {
            listener.onClickItem(note)
        }

    }

    override fun getItemCount() = notes.size

    fun setNotes(newNotes: MutableList<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note {
        return notes[position]
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.text_view_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.text_view_descriptions)
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)


    }

    interface OnClickListener {
        fun onClickItem(note: Note)
    }
}