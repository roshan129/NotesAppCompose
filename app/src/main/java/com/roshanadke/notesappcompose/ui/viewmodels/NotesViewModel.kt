package com.roshanadke.notesappcompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.db.NoteDao
import com.roshanadke.notesappcompose.db.NotesDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao
): ViewModel() {

    suspend fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }
}