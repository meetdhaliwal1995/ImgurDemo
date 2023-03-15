package com.example.interviewtask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageTable::class], version = 1)
abstract class ImageDataBase : RoomDatabase() {

    abstract fun imageDao(): ImagesDao

    companion object {
        @Volatile
        private var INSTANCE: ImageDataBase? = null

        fun getDatabase(context: Context): ImageDataBase {

            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, ImageDataBase::class.java, "ImageDB"
                        ).allowMainThreadQueries().fallbackToDestructiveMigrationFrom(2).build()
                    }
                }
            }

            return INSTANCE!!
        }
    }

}