package com.roshanadke.notesappcompose.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.notesappcompose.navigation.Screen
import com.roshanadke.notesappcompose.presentation.viewmodels.NotesViewModel
import com.roshanadke.notesappcompose.utils.Constants


@Composable
fun NotesMainScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val notes by viewModel.notes

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = Constants.KEY_NOTE,
                    value = null
                )
                navController.navigate(Screen.AddEditNotesScreen.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            LazyColumn {
                items(notes) {

                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = it.body, modifier = Modifier.clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = Constants.KEY_NOTE,
                                value = it
                            )
                            navController.navigate(Screen.AddEditNotesScreen.route)
                        })
                    }


                }
            }

        }

    }


}
