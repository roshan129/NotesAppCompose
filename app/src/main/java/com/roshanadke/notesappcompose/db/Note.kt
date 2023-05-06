package com.roshanadke.notesappcompose.db

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Entity
data class Note(
    var body: String,
    var updated_date: Long = System.currentTimeMillis(),
) {
    @PrimaryKey
    var id: Int? = null
    val createdDateFormatted: String
        get() = formatDate(updated_date)

    override fun toString(): String = Uri.encode(gson.toJson(this))

    companion object {
        const val DATE_FORMAT = "dd MMM yyyy hh:mm a"
        private val gson = Gson()
    }
}

private fun formatDate(timeInMillis: Long): String {
    val formatter = SimpleDateFormat(Note.DATE_FORMAT, Locale.getDefault())
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    return formatter.format(calendar.time)
}