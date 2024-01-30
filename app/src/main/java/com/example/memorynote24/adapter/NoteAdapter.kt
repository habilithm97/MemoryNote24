package com.example.memorynote24.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memorynote24.R
import com.example.memorynote24.databinding.ItemNoteBinding
import com.example.memorynote24.room.Note

// 데이터 클래스를 받음 -> 리스트 자체에서 데이터 리스트를 정의함
class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        // 두 아이템이 같은 객체인지 확인
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }
        // 두 아이템이 같은 데이터를 가지고 있는지 확인
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
           binding.note = note
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val binding = DataBindingUtil.inflate<ItemNoteBinding>(LayoutInflater.from(parent.context), R.layout.item_note, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getPosition(position: Int) : Note {
        return getItem(position)
    }
}