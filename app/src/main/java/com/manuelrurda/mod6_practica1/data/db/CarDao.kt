package com.manuelrurda.mod6_practica1.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.manuelrurda.mod6_practica1.data.db.model.CarEntity
import com.manuelrurda.mod6_practica1.utils.Constants

@Dao
interface CarDao {
    @Insert
    suspend fun insertCar(car: CarEntity)

    @Query("SELECT * FROM ${Constants.DB_CARS_TABLE}")
    suspend fun getAllCars(): List<CarEntity>

    @Update
    suspend fun updateCar(car: CarEntity)

    @Query("DELETE FROM ${Constants.DB_CARS_TABLE} WHERE id = :id")
    suspend fun deleteCar(id: Int)

}