package hu.kolos.karlovitz.incomeexpense.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InOutDao {

    @Query("SELECT * FROM inouts")
    fun getAllInOuts(): List<InOut>

    @Query("SELECT * FROM inouts WHERE way = :way")
    fun getSomeInOuts(way: String): List<InOut>

    @Query("SELECT sum(inouts.value) FROM inouts WHERE way = :way")
    fun sumOfInOuts(way: String): Int

    @Insert
    fun insertInOuts(vararg inouts: InOut)

    @Delete
    fun deleteInOuts(inouts: InOut)

    @Query("DELETE FROM inouts")
    fun deleteAllInOuts()



}