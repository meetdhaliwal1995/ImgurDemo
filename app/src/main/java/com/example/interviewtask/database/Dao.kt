package com.example.interviewtask.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    @Insert
    suspend fun insertImages(imageTable: ImageTable)

    @Update
    suspend fun updateImages(imageTable: ImageTable)

    @Delete
    suspend fun deleteImages(imageTable: ImageTable)

    @Query("SELECT * FROM ImageTable")
    fun getAll(): LiveData<List<ImageTable>>

}