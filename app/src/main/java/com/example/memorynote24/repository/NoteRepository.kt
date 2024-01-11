package com.example.memorynote24.repository

import com.example.memorynote24.room.Note
import com.example.memorynote24.room.NoteDao
import kotlinx.coroutines.flow.Flow

/**
 * Repository 패턴
  -데이터의 출처와 관계 없이 동일한 인터페이스로 데이터에 접근할 수 있도록 함
  -> Data Layer 캡슐화
 */
class NoteRepository(private val dao: NoteDao) {

    suspend fun addNote(note: Note) {
        dao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    val getAll: Flow<List<Note>> = dao.getAll()
}