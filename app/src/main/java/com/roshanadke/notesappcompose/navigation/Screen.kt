package com.roshanadke.notesappcompose.navigation

sealed class Screen(val route: String) {

    object NotesMainScreen: Screen("NotesMainScreen")
    object AddEditNotesScreen: Screen("AddEditNotesScreen")


}