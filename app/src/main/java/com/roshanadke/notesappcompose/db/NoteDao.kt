package com.roshanadke.notesappcompose.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Query("Select * from Note")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("Select * from Note where body LIKE '%' || :query || '%'")
    suspend fun getNotesBySearchQuery(query: String): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note): Int

    @Delete
    suspend fun deleteNote(note: Note): Int

    @Query("Delete FROM Note where id IN (:notes)")
    suspend fun deleteNotes(notes: List<Int>): Int

}