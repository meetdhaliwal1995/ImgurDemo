package com.example.interviewtask.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageTable")
data class ImageTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val link: String,
    val title: String
)
