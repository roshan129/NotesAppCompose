package com.roshanadke.notesappcompose.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.ui.viewmodels.NotesViewModel

@Composable
fun NotesMainScreen(
    notesViewModel: NotesViewModel = viewModel()
) {

    //first fetch notes list

    val tempList: List<Note> = mutableListOf(
        Note(
            "Test One"
        ),
        Note(
            "Test Two"
        ),
        Note(
            "Test Three"
        ),
    )



    var notesList: List<Note> = mutableListOf()
    LaunchedEffect(Unit) {
        notesViewModel.insertNote(
            Note(
                "Inserted Note"
            )
        )
        notesList = notesViewModel.getAllNotes()
    }

    if(notesList.isEmpty()) notesList = tempList

    //displayList
    LazyColumn {
        items(notesList) {
            NotesCard(body = it.body)
        }
    }

}

@Composable
fun NotesCard(body: String) {
    Text(text = body, modifier = Modifier.padding(12.dp))
}