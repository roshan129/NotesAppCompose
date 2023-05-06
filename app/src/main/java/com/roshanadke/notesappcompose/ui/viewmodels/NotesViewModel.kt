package com.roshanadke.notesappcompose.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.db.NoteDao
import com.roshanadke.notesappcompose.utils.ListTypeState
import com.roshanadke.notesappcompose.utils.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {


    var allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    private var _listTypeState: MutableState<ListTypeState> = mutableStateOf(value = ListTypeState.NormaList)
    val listTypeState: State<ListTypeState> = _listTypeState

    private var _searchWidgetState: MutableState<SearchWidgetState> = mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private var _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState
    fun changeListTypeState() {
        if(listTypeState.value == ListTypeState.NormaList) {
            _listTypeState.value = ListTypeState.GridList
        } else {
            _listTypeState.value = ListTypeState.NormaList
        }
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun getAllNotes() = viewModelScope.launch {
        allNotes = noteDao.getAllNotes() as MutableLiveData<List<Note>>
    }

    suspend fun insertNote(note: Note) = viewModelScope.launch {
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) = viewModelScope.launch {
        val a = noteDao.deleteNote(note)
    }

    suspend fun printLine(text: String) {
        Log.d("TAG", "printLine: text:  ${text}")
    }
}