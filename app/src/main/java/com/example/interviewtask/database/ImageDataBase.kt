package com.example.interviewtask.database

import android.annotation.SuppressLint
import android.media.Image
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ImageTable::class], version = 1)
abstract class ImageDataBase : RoomDatabase() {

    abstract fun imageDao(): ImagesDao

}