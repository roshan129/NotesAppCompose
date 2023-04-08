package com.roshanadke.notesappcompose.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.db.NoteDao
import com.roshanadke.notesappcompose.db.NotesDatabase
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {


    var allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun getAllNotes() = viewModelScope.launch {
        allNotes = noteDao.getAllNotes() as MutableLiveData<List<Note>>
    }

    suspend fun insertNote(note: Note) = viewModelScope.launch {
        val a = noteDao.insertNote(note)
        Log.d("TAG", "insertNote: $a")
    }


    suspend fun printLine(text: String) {
        Log.d("TAG", "printLine: text:  ${text}")
    }
}