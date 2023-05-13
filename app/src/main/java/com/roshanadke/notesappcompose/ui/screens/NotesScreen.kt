package com.roshanadke.notesappcompose.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.ui.viewmodels.NotesViewModel
import com.roshanadke.notesappcompose.utils.ListTypeState
import com.roshanadke.notesappcompose.utils.MultiSelectionState
import com.roshanadke.notesappcompose.utils.Screen
import com.roshanadke.notesappcompose.utils.SearchWidgetState
import com.roshanadke.notesappcompose.utils.getDisplayDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel = hiltViewModel(),
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    notesViewModel.getNotesBySearch(notesViewModel.searchTextState.value)
    val notes = notesViewModel.notesMainList
    val listTypeState by notesViewModel.listTypeState
    val searchWidgetState by notesViewModel.searchWidgetState
    val searchTextState by notesViewModel.searchTextState
    val multiSelectionState by notesViewModel.multiSelectionState
    val multiSelectedList by notesViewModel.multiSelectedListItems


    var notesList: List<Note> = remember {
        notes.value ?: emptyList()
    }
    notes.value?.let {
        notesList = it
    }


    Scaffold(
        topBar = {
            MainAppBar(
                multiSelectionList = multiSelectedList,
                searchWidgetState = searchWidgetState,
                multiSelectionState = multiSelectionState,
                searchTextState = searchTextState,
                listTypeState = listTypeState,
                onListTypeIconClicked = {
                    notesViewModel.changeListTypeState()
                },
                onTextChanged = {
                    notesViewModel.updateSearchTextState(it)
                },
                onSearchClicked = {
                    //notesViewModel.getNotesBySearch(searchTextState)
                },
                onCloseClicked = {
                    notesViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                    notesViewModel.updateSearchTextState("")
                },
                onSearchTriggered = {
                    notesViewModel.updateSearchWidgetState(SearchWidgetState.OPEN)
                },
                onMultiSelectionCloseClicked = {
                    notesViewModel.updateMultiSelectionState(MultiSelectionState.CLOSED)
                },
                onMultiSelectionDeleteClicked = {
                    if(multiSelectedList.isNotEmpty()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            notesViewModel.deleteMultipleNotes(multiSelectedList)
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
            if (multiSelectionState == MultiSelectionState.OPEN) {
                notesViewModel.addOrRemoveItemInMultiSelectedList(newItem = note)
            } else {
                navController.navigate(Screen.AddEditNotesScreen.route + "/${note}")
            }
        }

        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            Column {
                if (listTypeState == ListTypeState.NormaList) {
                    LazyColumn {
                        items(notesList) {
                            NotesCard(
                                multiSelectionList = multiSelectedList,
                                note = it,
                                onLongClickPressed = { note ->
                                    notesViewModel.updateMultiSelectionState(MultiSelectionState.OPEN)
                                    notesViewModel.addOrRemoveItemInMultiSelectedList(note)
                                },
                                onCardClicked = { note ->
                                    onCardItemClicked(note)
                                },
                                onDeleteClicked = { note ->
                                    coroutineScope.launch {
                                        notesViewModel.deleteNote(note)
                                    }
                                }
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                        items(notesList) {
                            NotesCard(
                                multiSelectionList = multiSelectedList,
                                note = it,
                                onLongClickPressed = {
                                    notesViewModel.updateMultiSelectionState(MultiSelectionState.OPEN)
                                },
                                onCardClicked = { note ->
                                    onCardItemClicked(note)
                                },
                                onDeleteClicked = { note ->
                                    coroutineScope.launch {
                                        notesViewModel.deleteNote(note)
                                    }
                                }
                            )
                        }
                    })
                }
            }
        }

    }

}


@Composable
fun MainAppBar(
    multiSelectionList: List<Note>,
    searchWidgetState: SearchWidgetState,
    multiSelectionState: MultiSelectionState,
    searchTextState: String,
    listTypeState: ListTypeState,
    onListTypeIconClicked: () -> Unit,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
    onMultiSelectionCloseClicked: () -> Unit,
    onMultiSelectionDeleteClicked: () -> Unit,
) {

    if (searchWidgetState == SearchWidgetState.CLOSED && multiSelectionState == MultiSelectionState.OPEN) {
        MultiSelectionAppBar(
            selectedItemsCount = multiSelectionList.size,
            onCloseClicked = {
                onMultiSelectionCloseClicked()
            },
            onDeleteClicked = {
                // delete selected notes
                onMultiSelectionDeleteClicked()
            }
        )
    } else if (searchWidgetState == SearchWidgetState.CLOSED) {
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

@Composable
fun MultiSelectionAppBar(
    selectedItemsCount: Int,
    onCloseClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            IconButton(onClick = {
                onCloseClicked()
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }

            Text(
                text = "$selectedItemsCount Items Selected",
                color = Color.Black,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                onDeleteClicked()
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }

        }
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        val focusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        TextField(
            value = text,
            onValueChange = {
                onTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .focusRequester(focusRequester = focusRequester),
            shape = RoundedCornerShape(24.dp),
            leadingIcon = {
                IconButton(onClick = {
                    onSearchClicked(text)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    onCloseClicked()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
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
                    keyboardController?.hide()
                }
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
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
    multiSelectionList: List<Note>,
    note: Note,
    onLongClickPressed: (note: Note) -> Unit,
    onCardClicked: (note: Note) -> Unit,
    onDeleteClicked: (note: Note) -> Unit,
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
                        //showDialog = true
                        onLongClickPressed(note)
                    }
                ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                if (multiSelectionList.contains(note)) Color.Magenta else Color.White
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
