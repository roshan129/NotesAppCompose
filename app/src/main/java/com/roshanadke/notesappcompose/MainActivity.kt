package com.roshanadke.notesappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.roshanadke.notesappcompose.ui.screens.NotesMainScreen
import com.roshanadke.notesappcompose.ui.screens.NotesScreen
import com.roshanadke.notesappcompose.ui.theme.NotesAppComposeTheme
import com.roshanadke.notesappcompose.ui.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    //NotesMainScreen()
                    NotesScreen()
                }
            }
        }
    }
}

