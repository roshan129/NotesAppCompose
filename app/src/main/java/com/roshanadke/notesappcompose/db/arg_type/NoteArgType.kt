package com.roshanadke.notesappcompose.db.arg_type

import com.google.gson.Gson
import com.roshanadke.notesappcompose.db.Note
import com.roshanadke.notesappcompose.utils.JsonNavType

class NoteArgType: JsonNavType<Note>() {
    override fun fromJsonParse(value: String): Note = Gson().fromJson(value, Note::class.java)

    override fun Note.getJsonParse(): String = Gson().toJson(this)
}