package com.roshanadke.notesappcompose.data.repository

import app.cash.turbine.test
import com.roshanadke.notesappcompose.common.NotesAndroidTest
import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.data.local.NoteDao
import com.roshanadke.notesappcompose.domain.repository.AddEditNotesRepository
import com.roshanadke.notesappcompose.domain.repository.NotesMainRepository
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class NotesInstrumentedTest : NotesAndroidTest() {

    @Inject
    lateinit var dao: NoteDao

    @Inject
    lateinit var notesMainRepository: NotesMainRepository

    @Inject
    lateinit var addEditNotesRepository: AddEditNotesRepository

    override fun setUp() {
        super.setUp()
    }

    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun testAddingNote_EditingNote_FetchingNote() = runTest {
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            notesMainRepository.getAllNotes().test {
                val note1 = Note(body = "first one")
                addEditNotesRepository.addNote(note1)

                val emission1 = awaitItem()
                println("emission1: $emission1")

                Assert.assertEquals(emission1.size, 1)
                Assert.assertEquals(emission1.get(0).body, "first one")

                val editedNote = emission1.get(0)
                editedNote.body = "edited one"

                addEditNotesRepository.editNote(editedNote)

                val emission2 = awaitItem()
                Assert.assertEquals(emission2.size, 1)
                Assert.assertEquals(emission2.get(0).body, "edited one")


            }
        }
    }


}