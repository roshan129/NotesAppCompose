package com.roshanadke.notesappcompose.domain.repository

import com.roshanadke.notesappcompose.data.local.Note

interface AddEditNotesRepository {


    suspend fun addNote(note: Note): Int

    suspend fun editNote(note: Note): Int


}