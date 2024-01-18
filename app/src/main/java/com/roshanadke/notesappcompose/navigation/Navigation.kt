package com.roshanadke.notesappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.roshanadke.notesappcompose.data.local.Note
import com.roshanadke.notesappcompose.presentation.screens.AddEditNotesScreen
import com.roshanadke.notesappcompose.presentation.screens.NotesMainScreen
import com.roshanadke.notesappcompose.utils.Constants

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.NotesMainScreen.route) {
        composable(Screen.NotesMainScreen.route) {
            NotesMainScreen(navController)
        }
        composable(Screen.AddEditNotesScreen.route) {
            val note: Note? = navController.previousBackStackEntry?.savedStateHandle?.get(Constants.KEY_NOTE)
            AddEditNotesScreen(navController, note =  note)
        }
    }




}