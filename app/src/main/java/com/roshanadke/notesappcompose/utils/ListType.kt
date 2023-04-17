package com.roshanadke.notesappcompose.utils

sealed class ListType(val type: String) {
    object NormaList: ListType("normal")
    object GridList: ListType("grid")
}
