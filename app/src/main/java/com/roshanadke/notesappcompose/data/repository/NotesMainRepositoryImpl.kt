package com.roshanadke.notesappcompose.data.repository

import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.data.local.NoteDao
import com.roshanadke.notesappcompose.domain.repository.NotesMainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

class NotesMainRepositoryImpl(
    private val dao: NoteDao
): NotesMainRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
    }


}
