package com.roshanadke.notesappcompose.db

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
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

    override fun toString(): String = Uri.encode(Gson().toJson(this))
}