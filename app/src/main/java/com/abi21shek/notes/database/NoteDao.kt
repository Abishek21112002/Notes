package com.abi21shek.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abi21shek.notes.Note
import java.util.*
import kotlin.collections.ArrayList

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id=(:id)")
    fun getNote(id: UUID): LiveData<Note?>

    @Update
    fun updateNote(note: Note)

    @Insert
    fun addNote(note: Note)

}