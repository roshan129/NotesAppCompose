package com.roshanadke.notesappcompose.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.ui.viewmodels.NotesViewModel
import com.roshanadke.notesappcompose.utils.ListTypeState
import com.roshanadke.notesappcompose.utils.Screen
import com.roshanadke.notesappcompose.utils.SearchWidgetState
import com.roshanadke.notesappcompose.utils.getDisplayDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel = hiltViewModel(),
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val notes = notesViewModel.allNotes.observeAsState()
    val listTypeState by notesViewModel.listTypeState
    val searchWidgetState by notesViewModel.searchWidgetState
    val searchTextState by notesViewModel.searchTextState


    var notesList: List<Note> = remember {
        notes.value ?: emptyList()
    }
    notes.value?.let {
        notesList = it
    }


    Scaffold(
        topBar = {
                 MainAppBar(
                     searchWidgetState = searchWidgetState,
                     searchTextState = searchTextState,
                     listTypeState = listTypeState,
                     onListTypeIconClicked = {
                         notesViewModel.changeListTypeState()
                     },
                     onTextChanged = {
                         notesViewModel.updateSearchTextState(it)
                     },
                     onSearchClicked = {
                         Log.d("TAG", "NotesScreen: search text: $it ")
                     },
                     onCloseClicked = {
                         notesViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                     },
                     onSearchTriggered = {
                         notesViewModel.updateSearchWidgetState(SearchWidgetState.OPEN)
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

                if (listTypeState == ListTypeState.NormaList) {
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


@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    listTypeState: ListTypeState,
    onListTypeIconClicked: () -> Unit,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit
) {

    if(searchWidgetState == SearchWidgetState.CLOSED) {
        DefaultAppBar(
            listTypeState = listTypeState,
            onListTypeIconClicked = onListTypeIconClicked,
            onSearchTriggered = onSearchTriggered
        )
    } else {
        SearchAppBar(
            text = searchTextState,
            onTextChanged = onTextChanged,
            onSearchClicked = onSearchClicked,
            onCloseClicked = onCloseClicked
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    listTypeState: ListTypeState,
    onListTypeIconClicked: () -> Unit,
    onSearchTriggered: () -> Unit
) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = "Notes App") },
        actions = {
            IconButton(onClick = {
                onListTypeIconClicked()

            }) {
                if (listTypeState == ListTypeState.NormaList) {
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
            IconButton(onClick = {
                Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show()
                onSearchTriggered()

            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search List"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(24.dp),
            leadingIcon = {
                IconButton(onClick = {
                    onSearchClicked(text)
                }) {
                    Icon(imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    onCloseClicked()
                }) {
                    Icon(imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            singleLine = true,
            colors =TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFD4E6FF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
            ),
        )

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
                    text = getDisplayDate(note.createdDateFormatted),
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
