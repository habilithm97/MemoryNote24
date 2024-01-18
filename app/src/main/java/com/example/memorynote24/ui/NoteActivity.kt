package com.example.memorynote24.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.memorynote24.R
import com.example.memorynote24.databinding.ActivityNoteBinding
import com.example.memorynote24.room.Note
import com.example.memorynote24.viewmodel.NoteViewModel

class NoteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNoteBinding.inflate(layoutInflater) }
    // ViewModelProvider를 사용하지 않고 viewModel 지연 생성 가능
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val title = binding.edtTitle.text.toString()
        val content = binding.edtContent.text.toString()

        if(title.isEmpty()) {
            Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show()
        } else {
            val note = Note(title, content)
            viewModel.addNote(note)
            finish()
        }
    }
}