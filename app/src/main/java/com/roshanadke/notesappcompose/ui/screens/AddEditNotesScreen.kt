package com.roshanadke.notesappcompose.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AddEditNotesScreen(note: String?) {

    note?.let {
        Log.d("TAG", "AddEditNotesScreen: ${note}")
        Text(text = note, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            textAlign = TextAlign.Center)
    }


}