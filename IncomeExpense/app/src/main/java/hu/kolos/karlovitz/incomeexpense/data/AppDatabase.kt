package hu.kolos.karlovitz.incomeexpense.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(InOut::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun inoutDao(): InOutDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        //fun getInstance(context: Context): AppDatabase {
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase::class.java, "inouts.db")
                    .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}