package com.manuelrurda.mod6_practica1.utils

import java.text.DecimalFormat

fun formatPrice(price: Double): String {
    val formatter: DecimalFormat = DecimalFormat("###,###,##0.00")
    return formatter.format(price)
}