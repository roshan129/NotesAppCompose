package com.roshanadke.notesappcompose.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Parcelize
@Entity
data class Note(
    @PrimaryKey
    var id: Int?= null,
    var body: String,
    var updated_date: Long = System.currentTimeMillis(),
): Parcelable {

    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(updated_date)
}