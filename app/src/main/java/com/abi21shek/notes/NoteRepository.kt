package com.abi21shek.notes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.abi21shek.notes.database.NoteDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "note-database"

class NoteRepository private constructor(context: Context){

    private val database: NoteDatabase = Room.databaseBuilder(
        context.applicationContext, NoteDatabase::class.java, DATABASE_NAME).build()

    private val noteDao = database.noteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getNotes(): LiveData<List<Note>> = noteDao.getNotes()
    fun getNote(id: UUID): LiveData<Note?> = noteDao.getNote(id)

    fun updateNote(note: Note){
        executor.execute {
            noteDao.updateNote(note)
        }
    }

    fun addNote(note: Note){
        executor.execute {
            noteDao.addNote(note)
        }
    }

    companion object{
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = NoteRepository(context)
            }
        }

        fun get(): NoteRepository {
            return INSTANCE ?:
            throw IllegalStateException("NoteRepository must be initialized")
        }
    }

}