package com.manuelrurda.mod6_practica1.data

import android.content.Context
import com.manuelrurda.mod6_practica1.data.db.CarDao
import com.manuelrurda.mod6_practica1.data.db.CarDatabase
import com.manuelrurda.mod6_practica1.data.db.model.CarEntity

object CarRepository {
    private lateinit var carDao: CarDao

    fun init(context: Context) {
        val database = CarDatabase.getDatabase(context)
        carDao = database.carDao()
    }
    
    suspend fun insertCar(car: CarEntity) {
        carDao.insertCar(car)
    }

    suspend fun getAllCars(): List<CarEntity> {
        return carDao.getAllCars()
    }

    suspend fun updateCar(car: CarEntity) {
        carDao.updateCar(car)
    }

    suspend fun deleteCar(id: Int) {
        carDao.deleteCar(id)

    }
}