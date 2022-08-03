package com.abi21shek.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note (@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var notes: String = "",
                 var date: Date = Date())