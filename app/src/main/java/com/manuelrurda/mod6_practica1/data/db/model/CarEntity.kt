package com.manuelrurda.mod6_practica1.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manuelrurda.mod6_practica1.utils.Constants

@Entity(tableName = Constants.DB_CARS_TABLE)
data class CarEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "brand") var brand: String,
    @ColumnInfo(name = "model") var model: String,
    @ColumnInfo(name = "year") var year: Int,
    @ColumnInfo(name = "price") var price: Double
)

enum class CarBrand(val displayName: String) {
    MERCEDES("Mercedes-Benz"),
    LEXUS("Lexus"),
    JAGUAR("Jaguar"),
    MAZDA("Mazda"),
    PORSCHE("Porsche")
}
