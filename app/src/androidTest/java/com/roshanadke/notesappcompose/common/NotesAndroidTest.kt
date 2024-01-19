package com.roshanadke.notesappcompose.common

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.roshanadke.notesappcompose.data.local.NoteDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class NotesAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: NoteDatabase

    protected lateinit var context: Context

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
        db.clearAllTables()
    }


    @After
    open fun tearDown() {
        db.close()
    }
}