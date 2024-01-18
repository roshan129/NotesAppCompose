package com.roshanadke.notesappcompose.domain.repository

import com.roshanadke.notesappcompose.data.local.Note
import kotlinx.coroutines.flow.Flow

interface NotesMainRepository {


    fun getAllNotes(): Flow<List<Note>>



}