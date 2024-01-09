package com.example.memorynote24.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // CUD 작업은 시간이 걸리는 작업이기 때문에 코루틴 안에서 비동기적 수행을 함 -> suspend 키워드 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE) // 동일한 항목이면 덮어씀
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("select * from note order by id asc")
    fun getAll(): Flow<List<Note>> // 비동기 데이터 스트림 Flow
}