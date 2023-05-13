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
import com.roshanadke.notesappcompose.utils.MultiSelectionState
import com.roshanadke.notesappcompose.utils.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {


    private var _notesMainList: MutableState<List<Note>> = mutableStateOf(arrayListOf())
    val notesMainList: State<List<Note>> = _notesMainList
    //val notesMainList: State<Note> get() = _notesMainList!!


    var allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    private var _listTypeState: MutableState<ListTypeState> =
        mutableStateOf(value = ListTypeState.NormaList)
    val listTypeState: State<ListTypeState> = _listTypeState

    private var _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private var _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private var _multiSelectionState: MutableState<MultiSelectionState> =
        mutableStateOf(MultiSelectionState.CLOSED)
    val multiSelectionState: State<MultiSelectionState> = _multiSelectionState

    private var _multiSelectedListItems: MutableState<List<Note>> = mutableStateOf(arrayListOf())
    val multiSelectedListItems: State<List<Note>> = _multiSelectedListItems

    fun getNotesBySearch(searchText: String) = viewModelScope.launch {
        _notesMainList.value = noteDao.getNotesBySearchQuery(searchText)
    }

    fun changeListTypeState() {
        if (listTypeState.value == ListTypeState.NormaList) {
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

    fun updateMultiSelectionState(newValue: MultiSelectionState) {
        _multiSelectionState.value = newValue
        if (newValue == MultiSelectionState.CLOSED) {
            emptyMultiSelectionList()
        }
    }

    private fun emptyMultiSelectionList() {
        _multiSelectedListItems.value = emptyList()
    }

    fun addOrRemoveItemInMultiSelectedList(newItem: Note) {
        if (_multiSelectedListItems.value.contains(newItem)) {
            _multiSelectedListItems.value =
                _multiSelectedListItems.value.filterNot { it == newItem }
        } else {
            _multiSelectedListItems.value = _multiSelectedListItems.value + newItem

        }
    }

    fun getAllNotes() = viewModelScope.launch {
        allNotes = noteDao.getAllNotes() as MutableLiveData<List<Note>>
    }

    suspend fun insertNote(note: Note) = viewModelScope.launch {
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) = viewModelScope.launch {
        noteDao.deleteNote(note)
        getNotesBySearch("")
    }

    suspend fun deleteMultipleNotes(note: List<Note>) = viewModelScope.launch {
        val noteIdList = note.map {
            it.id!!
        }
        noteIdList.let {
            noteDao.deleteNotes(noteIdList)
        }
        emptyMultiSelectionList()
        getNotesBySearch("")
    }

    suspend fun printLine(text: String) {
        Log.d("TAG", "printLine: text:  ${text}")
    }
}