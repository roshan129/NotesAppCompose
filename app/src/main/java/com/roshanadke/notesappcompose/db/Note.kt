package com.roshanadke.notesappcompose.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity
data class Note(
    var body: String,
    var updated_date: Long = System.currentTimeMillis(),
) {
    @PrimaryKey
    var id: Int?= null
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(updated_date)
}