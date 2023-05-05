package com.roshanadke.notesappcompose.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.ui.viewmodels.NotesViewModel
import com.roshanadke.notesappcompose.utils.ListType
import com.roshanadke.notesappcompose.utils.Screen
import com.roshanadke.notesappcompose.utils.formatDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel = hiltViewModel(),
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    val notes = notesViewModel.allNotes.observeAsState()
    var notesList: List<Note> = remember {
        notes.value ?: emptyList()
    }
    notes.value?.let {
        notesList = it
    }

    var listType by rememberSaveable {
        mutableStateOf(ListType.NormaList.type)
    }

    val note = Note("abcd")

    Log.d("TAG", "NotesScreen a: ${note.toString()} ")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes App") },
                actions = {
                    IconButton(onClick = {
                        listType = if (listType == ListType.NormaList.type) {
                            ListType.GridList.type
                        } else {
                            ListType.NormaList.type
                        }
                    }) {
                        if (listType == ListType.NormaList.type) {
                            Icon(
                                imageVector = Icons.Filled.List,
                                contentDescription = "Normal List"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.ThumbUp,
                                contentDescription = "Grid List"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val emptyNote = Note("")
                navController.navigate(Screen.AddEditNotesScreen.route + "/${emptyNote}")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Note")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { contentPadding ->

        fun onCardItemClicked(note: Note) {
            navController.navigate(Screen.AddEditNotesScreen.route + "/${note}")
        }

        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            Column {

                if (listType == ListType.NormaList.type) {
                    LazyColumn {
                        items(notesList) {
                            NotesCard(it, onCardClicked = { note ->
                                onCardItemClicked(note)
                            }) { note ->
                                coroutineScope.launch {
                                    notesViewModel.deleteNote(note)
                                }
                            }
                        }
                    }
                } else {
                    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                        items(notesList) {
                            NotesCard(it, onCardClicked = { note ->
                                onCardItemClicked(note)
                            }) { note ->
                                coroutineScope.launch {
                                    notesViewModel.deleteNote(note)
                                }
                            }
                        }
                    })
                }

            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesCard(
    note: Note,
    onCardClicked: (note: Note) -> Unit,
    onDeleteClicked: (note: Note) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {

        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            MyDialogBox(onDismissClicked = { showDialog = false },
                onConfirmClicked = {
                    onDeleteClicked(note)
                    showDialog = false
                })
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        onCardClicked.invoke(note)
                    },
                    onLongClick = {
                        //show dialog popup
                        showDialog = true
                    }
                ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
                /*containerColor = MaterialTheme.colorScheme.surfaceVariant,*/
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),


            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = note.body,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(12.dp),
                )
                Text(
                    text = formatDate(note.createdDateFormatted),
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp),
                    textAlign = TextAlign.End,
                    fontSize = 12.sp
                )

            }
        }


    }

}

@Composable
fun MyDialogBox(onConfirmClicked: () -> Unit, onDismissClicked: () -> Unit) {


    AlertDialog(
        onDismissRequest = onDismissClicked,
        title = { Text("Confirm!") },
        text = { Text("Do you want to delete this note?") },
        confirmButton = {
            Button(
                onClick = onConfirmClicked
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(onClick = onDismissClicked) {
                Text("No")
            }
        }
    )
}
