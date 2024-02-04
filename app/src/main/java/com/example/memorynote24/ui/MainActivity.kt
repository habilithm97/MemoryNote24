package com.example.memorynote24.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorynote24.R
import com.example.memorynote24.adapter.NoteAdapter
import com.example.memorynote24.databinding.ActivityMainBinding
import com.example.memorynote24.room.Note
import com.example.memorynote24.ui.NoteActivity.Companion.INSERT
import com.example.memorynote24.ui.NoteActivity.Companion.UPDATE
import com.example.memorynote24.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity, R.layout.activity_main) }
    private val noteAdapter by lazy { NoteAdapter() }
    private val viewModel: NoteViewModel by viewModels() // ViewModelProvider를 사용하지 않고 viewModel 지연 생성 가능
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding.apply {
            fab.setOnClickListener {
                val intent = Intent(this@MainActivity, NoteActivity::class.java)
                resultLauncher.launch(intent)
            }
            rv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity).apply {
                    reverseLayout = true
                    stackFromEnd = true
                }
                setHasFixedSize(true) // 고정된 사이즈의 RecyclerView -> 불필요한 리소스 줄이기
                adapter = noteAdapter
            }
        }

        // 리스트를 관찰하여 변경 시 어댑터에 전달함
        viewModel.getAll.observe(this, Observer {
            noteAdapter.submitList(it)
        })

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteAdapter.getPosition(position)

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        deleteDialog(note)
                    }
                }
            }
        }
        ItemTouchHelper(itemTouchCallback).apply {
            attachToRecyclerView(binding.rv)
        }

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            val intent = result.data
            if(result.resultCode == INSERT) { // 추가 모드
                if(intent != null) {
                    val title = intent.getStringExtra(NoteActivity.TITLE).toString()
                    val content = intent.getStringExtra(NoteActivity.CONTENT).toString()
                    val note = Note(title, content)
                    viewModel.addNote(note)
                }
            } else if(result.resultCode == UPDATE) { // 수정 모드
                if (intent != null) {
                    val id = intent.getIntExtra(NoteActivity.ID, -1)

                    if (id == -1) {
                        return@registerForActivityResult
                    }

                    val title = intent.getStringExtra(NoteActivity.TITLE).toString()
                    val content = intent.getStringExtra(NoteActivity.CONTENT).toString()
                    val note = Note(title, content)
                    note.id = id
                    viewModel.updateNote(note)
                }
            }
        }
    }

    private fun deleteDialog(note: Note) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.delete_title)
            .setMessage(R.string.delete_msg)
            .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, i ->
                viewModel.deleteNote(note)
                Toast.makeText(this, R.string.delete, Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, i ->
                dialog.dismiss()
            })
        builder.show()
    }
}