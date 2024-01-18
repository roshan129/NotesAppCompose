package com.roshanadke.notesappcompose.data.repository

import com.roshanadke.notesappcompose.data.NoteDaoFake
import com.roshanadke.notesappcompose.data.local.Note
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class AddEditNotesRepositoryImplTest {

    private lateinit var noteDaoFake: NoteDaoFake
    private lateinit var notesMainRepository: NotesMainRepositoryImpl

    @Before
    fun setUp() {
        noteDaoFake = NoteDaoFake()
        notesMainRepository = NotesMainRepositoryImpl(noteDaoFake)
    }


    @Test
    fun `test adding new note`(): Unit = runBlocking {
        val note = Note("abcd")
        noteDaoFake.insertNote(note)
        assert(noteDaoFake.notesList.size == 1)
    }

    @Test
    fun `test adding and editing note`(): Unit = runBlocking {
        val note = Note("abcd")
        noteDaoFake.insertNote(note)
        assert(noteDaoFake.notesList.size == 1)

        note.body = "pqrs"
        noteDaoFake.updateNote(note)
        assert(noteDaoFake.notesList[0].body == "pqrs")
    }



    @After
    fun tearDown() {

    }
}