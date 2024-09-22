package com.example.noteapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteDb")
data class Note(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "descriptions") val descriptions: String?
)