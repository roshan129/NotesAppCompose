package com.roshanadke.notesappcompose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.db.arg_type.NoteArgType
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
                type = NoteArgType()
            })
        ) { navBackStackEntry ->
            Log.d("TAG", "Navigation: note string : ${navBackStackEntry.arguments?.getString("noteBody")} ")
            val note = navBackStackEntry.arguments?.getString("noteBody")?.let { Gson().fromJson(it, Note::class.java) }
            AddEditNotesScreen(
                navController = navController,
                note = note
            )
        }

    }

}