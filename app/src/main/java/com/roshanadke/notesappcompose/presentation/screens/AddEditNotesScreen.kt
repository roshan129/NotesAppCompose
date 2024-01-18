package com.roshanadke.notesappcompose.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.presentation.viewmodels.AddEditNotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNotesScreen(
    navController: NavController,
    viewModel: AddEditNotesViewModel = hiltViewModel(),
    note: Note?
) {

    val isEditMode = rememberSaveable {
        note != null
    }

    var input by rememberSaveable { mutableStateOf(note?.body ?: "") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (isEditMode) {
                    note?.let {
                        note.body = input
                        viewModel.editNote(note)
                    }
                } else {
                    val newNote = Note(body = input)
                    viewModel.addNote(newNote)
                }
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save"
                )
            }
        },
        topBar = {
            TopAppBar(title = {

            },

                navigationIcon = {
                    IconButton(modifier = Modifier.padding(4.dp), onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
        }
    ) { it ->

        Box(
            Modifier.padding(it)
        ) {
            TextField(
                value = input, onValueChange = {
                    input = it
                }, modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }

    }


}