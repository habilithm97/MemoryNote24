package com.example.memorynote24.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.memorynote24.repository.NoteRepository
import com.example.memorynote24.room.Note
import com.example.memorynote24.room.NoteDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * AAC ViewModel
  -Lifecycle를 고려하여 View 데이터를 저장 및 관리함
 */

// Context를 가지기 위한 AndroidViewModel 클래스 상속
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    val getAll: LiveData<List<Note>>

    init {
        val dao = NoteDB.getInstance(application)!!.dao()
        repository = NoteRepository(dao)
        getAll = repository.getAll.asLiveData()
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }
}