package com.roshanadke.notesappcompose.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.data.local.NoteDao
import com.roshanadke.notesappcompose.domain.repository.AddEditNotesRepository
import com.roshanadke.notesappcompose.domain.repository.NotesMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesMainRepository,
): ViewModel() {

    private var _notes = mutableStateOf<List<Note>>(emptyList())
    val notes: State<List<Note>> = _notes

    init {
        getAllNotes()
    }

    fun getAllNotes() {
        repository.getAllNotes().onEach {
            Timber.d("list size: ${it.size}")
            _notes.value = it
        }.launchIn(viewModelScope)
    }

}