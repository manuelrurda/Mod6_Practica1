package com.manuelrurda.mod6_practica1.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manuelrurda.mod6_practica1.data.CarRepository
import com.manuelrurda.mod6_practica1.data.db.model.CarBrand
import com.manuelrurda.mod6_practica1.data.db.model.CarEntity
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var selectedCarEntity by remember { mutableStateOf(null as CarEntity?) }

    val coroutineScope = rememberCoroutineScope()

    val data = remember { mutableStateOf<List<CarEntity>>(emptyList()) }
    LaunchedEffect(Unit) {
        CarRepository.init(context)
        data.value = CarRepository.getAllCars()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(data.value) {
                CarListItem(car = it, onClick = {
                    showDialog = true
                    selectedCarEntity = it
                })
            }
        }
        FloatingActionButton(
            onClick = {
                showDialog = true
            }, shape = CircleShape, modifier =
            Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, "Add car")
        }

        if (showDialog) {
            CarDialog(showDialog = showDialog, onDismiss = {
                showDialog = false
                selectedCarEntity = null
                coroutineScope.launch {
                    data.value = CarRepository.getAllCars()
                }
            }, initialData = selectedCarEntity)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}