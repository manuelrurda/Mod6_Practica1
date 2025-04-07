package com.manuelrurda.mod6_practica1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manuelrurda.mod6_practica1.data.db.model.CarEntity
import com.manuelrurda.mod6_practica1.utils.Constants

@Database(entities = [CarEntity::class], version = 1, exportSchema = true)
abstract class CarDatabase: RoomDatabase() {

    abstract fun carDao(): CarDao

    companion object{
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    Constants.DB_NAME).build()

                INSTANCE = instance
                instance
            }
        }
    }
}