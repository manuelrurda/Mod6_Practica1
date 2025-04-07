package com.manuelrurda.mod6_practica1.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manuelrurda.mod6_practica1.R
import com.manuelrurda.mod6_practica1.data.db.model.CarBrand
import com.manuelrurda.mod6_practica1.data.db.model.CarEntity
import com.manuelrurda.mod6_practica1.ui.theme.Gray
import com.manuelrurda.mod6_practica1.utils.formatPrice

@Composable
fun CarListItem(car: CarEntity, onClick: (car: CarEntity) -> Unit = {}) {
    val carLogo = when (car.brand) {
        CarBrand.MERCEDES.displayName -> R.drawable.mercedes
        CarBrand.LEXUS.displayName -> R.drawable.lexus
        CarBrand.JAGUAR.displayName -> R.drawable.jaguar
        CarBrand.MAZDA.displayName -> R.drawable.mazda
        CarBrand.PORSCHE.displayName -> R.drawable.porsche
        else -> R.drawable.ic_launcher_foreground
    }

    ElevatedCard(
        modifier = Modifier.clickable {
            onClick(car)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(carLogo),
                    contentDescription = stringResource(id = R.string.description_logo),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(car.brand, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(
                        "${car.model} • ${car.year}",
                        style = TextStyle(fontSize = 12.sp, color = Gray)
                    )
                }
            }
            Text("$ ${formatPrice(car.price)}")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CarListItemPreview() {
    val car = CarEntity(
        id = 1,
        brand = CarBrand.MERCEDES.displayName,
        model = "C-Class",
        year = 2020,
        price = 45000.0
    )
    CarListItem(car = car)
}