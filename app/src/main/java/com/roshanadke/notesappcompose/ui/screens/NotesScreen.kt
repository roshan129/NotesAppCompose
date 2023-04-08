package com.roshanadke.notesappcompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.ui.viewmodels.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes App") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

            }) {

                Icon(Icons.Filled.Add, contentDescription = "Add Note")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { contentPadding ->

        Box(
            modifier = Modifier.padding(contentPadding)
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


}
