package com.example.noteapplication.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application, private var noteDao: NoteDao) :
    AndroidViewModel(application) {

    var repository: NoteRepository
    var allNotes: LiveData<MutableList<Note>>

    init {
        noteDao = NoteDatabase.getNoteDatabase(application).getNoteDao()
        repository = NoteRepository(noteDao)
        allNotes = repository.allNote
    }


    fun insert(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun deleteAllNote(){
        viewModelScope.launch {
            repository.deleteAllNote()
        }
    }
}