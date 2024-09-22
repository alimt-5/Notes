package com.example.noteapplication.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)


    @Query("SELECT * FROM NoteDb ORDER BY id ASC")
    fun getAllNotes(): LiveData<MutableList<Note>>

    @Query("DELETE FROM NoteDb")
    suspend fun deleteAllNotes()


}