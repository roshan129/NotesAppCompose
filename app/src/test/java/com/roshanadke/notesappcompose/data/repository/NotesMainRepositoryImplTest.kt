package com.roshanadke.notesappcompose.data.repository

import app.cash.turbine.test
import com.roshanadke.notesappcompose.data.NoteDaoFake
import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.domain.repository.NotesMainRepository
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


internal class NotesMainRepositoryImplTest {


    private lateinit var noteDaoFake: NoteDaoFake
    private lateinit var notesMainRepository: NotesMainRepositoryImpl

    @Before
    fun setUp() {
        noteDaoFake = NoteDaoFake()
        notesMainRepository = NotesMainRepositoryImpl(noteDaoFake)
    }
    @Test
    fun `test fetching notes`() = runTest {
        noteDaoFake.insertNote(Note("new1"))
        noteDaoFake.insertNote(Note("new2"))
        notesMainRepository.getAllNotes().test {
            val emission1 = awaitItem()
            Assert.assertEquals(emission1.size, 2)
            Assert.assertEquals(emission1.get(0).body, "new1")
            Assert.assertEquals(emission1.get(1).body, "new2")

            awaitComplete()
        }
    }

}