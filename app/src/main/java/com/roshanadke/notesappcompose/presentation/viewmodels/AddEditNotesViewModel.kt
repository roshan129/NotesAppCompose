package com.roshanadke.notesappcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.domain.repository.AddEditNotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNotesViewModel @Inject constructor(
    private val repository: AddEditNotesRepository
): ViewModel() {

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.addNote(note = note)
        }
    }

    fun editNote(note: Note) {
        viewModelScope.launch {
            repository.editNote(note = note)
        }
    }



}