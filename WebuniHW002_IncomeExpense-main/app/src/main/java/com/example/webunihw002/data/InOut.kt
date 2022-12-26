package com.example.webunihw002.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inouts")
data class InOut(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "income") var income: Int,
    @ColumnInfo(name = "expense") var expense: Int
)