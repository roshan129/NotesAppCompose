package com.roshanadke.notesappcompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.roshanadke.notesappcompose.ui.screens.AddEditNotesScreen
import com.roshanadke.notesappcompose.ui.screens.NotesScreen
import com.roshanadke.notesappcompose.utils.Screen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.NotesScreen.route) {

        composable(Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
        }

        composable(
            Screen.AddEditNotesScreen.route + "/{noteBody}",
            arguments = listOf(navArgument("noteBody") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { navBackStackEntry ->
            AddEditNotesScreen(note = navBackStackEntry.arguments?.getString("noteBody"))
        }

    }

}