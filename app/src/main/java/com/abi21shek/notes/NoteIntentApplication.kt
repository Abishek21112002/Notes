package com.abi21shek.notes

import android.app.Application

class NoteIntentApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }

}