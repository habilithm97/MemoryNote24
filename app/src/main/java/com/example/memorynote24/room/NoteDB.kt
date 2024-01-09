package com.example.memorynote24.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDB : RoomDatabase() {
    abstract fun dao(): NoteDao

    companion object { // 싱글톤
        @Volatile // 싱글톤 보장(해당 데이터가 메인 메모리에만 존재하도록 명시)
        private var instance: NoteDB? = null

        fun getInstance(context: Context): NoteDB? {
            if(instance == null) {
                synchronized(NoteDB::class) { // 동기화(오직 하나의 스레드만 접근 가능하도록 Lock)
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDB::class.java,
                        "note_database"
                    ).build()
                }
            }
            return instance
        }
    }
}