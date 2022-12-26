package com.example.webunihw002.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface InOutDAO {


    @Query("SELECT * FROM inouts")
    fun getAllGrades(): List<InOut>

    @Query("SELECT sum(inouts.income) FROM inouts")
    fun sumOfIncomes(): Int

    @Query("SELECT sum(inouts.expense) FROM inouts")
    fun sumOfExpenses(): Int

    @Insert
    fun insertGrades(vararg grades: InOut)

    @Delete
    fun deleteGrade(grade: InOut)

}