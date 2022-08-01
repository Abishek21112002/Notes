package com.abi21shek.notes

import androidx.lifecycle.ViewModel

class NoteListViewModel: ViewModel() {

    val hello = 89

    val notes = mutableListOf<Note>()

    init {
        for (i in 1..50){
            val note = Note()
            note.title = "Note #$i"
            note.notes = "Dummy"
            notes += note
        }
    }
}