package com.roshanadke.notesappcompose.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.ui.viewmodels.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddEditNotesScreen(
    navController: NavController,
    note: Note?,
    notesViewModel: NotesViewModel = hiltViewModel(),
) {

    val coroutineScope = rememberCoroutineScope()

    var noteBody by rememberSaveable {
        mutableStateOf(note?.body ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (note?.body.isNullOrEmpty()) "Add Note" else "Edit Note",
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                Log.d("TAG", "AddEditNotesScreen: back button clicked n ")
                                //backDispatcher?.onBackPressed()
                                navController.popBackStack()

                            },
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                coroutineScope.launch {
                    noteBody.let { body ->
                        note?.let {
                            it.body = body
                            notesViewModel.insertNote(
                                note
                            )
                        }
                    }
                    navController.popBackStack()
                }

            }) {
                Icon(Icons.Filled.Check, contentDescription = "Save Note")
            }
        }

    ) { it ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            val focusRequester = remember { FocusRequester() }

            LaunchedEffect(Unit) {
                if(noteBody.isEmpty()) focusRequester.requestFocus()
            }

            TextField(
                value = noteBody,
                onValueChange = {
                    noteBody = it
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .focusRequester(focusRequester = focusRequester)

            )
        }

    }


}