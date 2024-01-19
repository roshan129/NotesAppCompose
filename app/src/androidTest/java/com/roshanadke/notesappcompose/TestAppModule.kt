package com.roshanadke.notesappcompose

import android.content.Context
import androidx.room.Room
import com.roshanadke.notesappcompose.data.local.NoteDao
import com.roshanadke.notesappcompose.data.local.NoteDatabase
import com.roshanadke.notesappcompose.data.repository.AddEditNotesRepositoryImpl
import com.roshanadke.notesappcompose.data.repository.NotesMainRepositoryImpl
import com.roshanadke.notesappcompose.di.AppModule
import com.roshanadke.notesappcompose.domain.repository.AddEditNotesRepository
import com.roshanadke.notesappcompose.domain.repository.NotesMainRepository
import com.roshanadke.notesappcompose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).allowMainThreadQueries().build()

    }

    @Provides
    @Singleton
    fun provideNotesDao(
        db: NoteDatabase
    ): NoteDao {
        return db.getNoteDao()
    }

    @Provides
    @Singleton
    fun provideNotesMainRepository(
        dao: NoteDao
    ): NotesMainRepository {
        return NotesMainRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideAddEditNotesRepository(
        dao: NoteDao
    ): AddEditNotesRepository {
        return AddEditNotesRepositoryImpl(dao)
    }


}