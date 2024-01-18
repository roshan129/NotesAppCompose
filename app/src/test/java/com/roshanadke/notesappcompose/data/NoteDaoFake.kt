package com.roshanadke.notesappcompose.data

import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class NoteDaoFake : NoteDao {

    var notesList = mutableListOf<Note>()

    override fun getAllNotes(): Flow<List<Note>> {
        return flow {
            emit(notesList)
        }
    }

    override suspend fun insertNote(note: Note): Long {
        notesList.add(note)
        return 1
    }

    override suspend fun updateNote(note: Note): Int {
        notesList.map {
            if (it.id == note.id) {
                note
            } else it
        }
        return 1
    }

}