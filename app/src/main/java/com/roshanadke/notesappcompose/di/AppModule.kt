package com.roshanadke.notesappcompose.di

import android.content.Context
import androidx.room.Room
import com.roshanadke.notesappcompose.db.NotesDatabase
import com.roshanadke.notesappcompose.utils.Constants.NOTE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext app: Context) = app

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        NOTE_DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideNoteDao(db: NotesDatabase) = db.getNoteDao()


}





