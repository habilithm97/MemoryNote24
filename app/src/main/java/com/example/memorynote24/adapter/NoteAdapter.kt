package com.example.memorynote24.adapter

import android.view.LayoutInflater
import android.view.View
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
    private lateinit var listener: OnItemClickListener // 전달된 리스너 객체를 저장할 변수

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

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // 아이템 클릭 이벤트 핸들러 메서드에서 리스너 객체 메서드 호출
                itemView.setOnClickListener {
                    listener.onItemClick(itemView, note, position)
                }
            }
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

    interface OnItemClickListener { // 커스텀 리스너 인터페이스
        fun onItemClick(view: View, note: Note, position: Int)
    }

    // 리스너 객체를 전달하는 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}