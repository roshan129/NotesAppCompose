package com.roshanadke.notesappcompose.utils

sealed class ListTypeState(val type: String) {
    object NormaList: ListTypeState("normal")
    object GridList: ListTypeState("grid")
}
