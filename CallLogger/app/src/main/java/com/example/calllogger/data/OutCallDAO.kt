package com.example.calllogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OutCallDAO {

    @Query("SELECT * FROM outcall")
    fun getAllCalls() : LiveData<List<OutCallEntity>>

    @Insert
    fun addCall(outCallEntity: OutCallEntity) : Long

    @Delete
    fun deleteCall(outCallEntity: OutCallEntity)
}