package com.example.memorynote24.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(val title: String, val content: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}