package com.example.memorynote24.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.memorynote24.R
import com.example.memorynote24.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNoteBinding.inflate(layoutInflater) }
    private var id = -1

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val INSERT = 0
        const val UPDATE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            btnCancel.setOnClickListener {
                finish()
            }
            btnSave.setOnClickListener {
                save()
            }
        }
        val intent = intent
        if(intent.hasExtra(ID)) { // 전달 받은 intent에 id 값이 있으면 수정 모드
            id = intent.getIntExtra(ID, -1)
            binding.edtTitle.setText(intent.getStringExtra(TITLE))
            binding.edtContent.setText(intent.getStringExtra(CONTENT))
        } else { // 추가 모드
            id = intent.getIntExtra(ID, -1)
        }
    }

    private fun save() {
        val title = binding.edtTitle.text.toString()
        val content = binding.edtContent.text.toString()

        if(title.trim().isEmpty()) {
            Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent()
            intent.putExtra(TITLE, title)
            intent.putExtra(CONTENT, content)

            if(id != -1) { // 수정 모드
                intent.putExtra(ID, id)
                setResult(UPDATE, intent)
            } else { // 추가 모드
                setResult(INSERT, intent)
            }
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        binding.edtTitle.requestFocus()
    }
}