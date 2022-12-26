package hu.kolos.karlovitz.incomeexpense.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "inouts")
data class InOut(
    @PrimaryKey(autoGenerate = true) val inoutId: Long?,
    @ColumnInfo(name = "way") var way: String?,
    @ColumnInfo(name = "value") var value: Int
)

