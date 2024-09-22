package com.example.noteapplication.room

import androidx.lifecycle.LiveData

class NoteRepository(private var noteDao: NoteDao) {

    val allNote: LiveData<MutableList<Note>> = noteDao.getAllNotes()


    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteAllNote(){
        noteDao.deleteAllNotes()
    }

}