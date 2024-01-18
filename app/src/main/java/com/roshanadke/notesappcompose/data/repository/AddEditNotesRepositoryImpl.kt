package com.roshanadke.notesappcompose.data.repository

import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.data.local.NoteDao
import com.roshanadke.notesappcompose.domain.repository.AddEditNotesRepository
import com.roshanadke.notesappcompose.domain.repository.NotesMainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

class AddEditNotesRepositoryImpl(
    private val dao: NoteDao
): AddEditNotesRepository {

    override suspend fun addNote(note: Note): Int {
        return dao.insertNote(note = note).toInt()
    }

    override suspend fun editNote(note: Note): Int {
        return dao.updateNote(note)
    }
}
