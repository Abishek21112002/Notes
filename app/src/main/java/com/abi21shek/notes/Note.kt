package com.abi21shek.notes

import java.util.*

data class Note ( val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var notes: String = "",
                var date: Date = Date())