package com.roshanadke.notesappcompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.ui.viewmodels.NotesViewModel
import kotlinx.coroutines.launch

@Composable
fun NotesMainScreen(
    notesViewModel: NotesViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val notes = notesViewModel.allNotes.observeAsState()
    var notesList: List<Note> = remember {
        notes.value ?: emptyList()
    }
    notes.value?.let {
        notesList = it
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Button(
                onClick = {
                    coroutineScope.launch {
                        notesViewModel.insertNote(
                            Note(
                                "Inserted Note ${notesList.size}"
                            )
                        )

                    }

                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Insert Note")
            }


            LazyColumn {
                items(notesList) {
                    NotesCard(body = it.body)
                }
            }
        }
    }


}

@Composable
fun NotesCard(body: String) {
    Text(text = body, modifier = Modifier.padding(12.dp))
}