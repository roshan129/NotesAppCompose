package com.roshanadke.notesappcompose.utils

import com.roshanadke.notesappcompose.db.Note
import java.text.SimpleDateFormat
import java.util.Locale


fun getDisplayDate(inputDate: String): String {
    //val inputFormat = SimpleDateFormat("dd-MMM-yyyy h:mm:ss a", Locale.getDefault())
    val inputFormat = SimpleDateFormat(Note.DATE_FORMAT, Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM h:mm a", Locale.getDefault())

    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}