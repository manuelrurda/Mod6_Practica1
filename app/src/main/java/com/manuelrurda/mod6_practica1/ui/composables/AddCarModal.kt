package com.manuelrurda.mod6_practica1.ui.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.manuelrurda.mod6_practica1.R
import com.manuelrurda.mod6_practica1.data.CarRepository
import com.manuelrurda.mod6_practica1.data.db.model.CarBrand
import com.manuelrurda.mod6_practica1.data.db.model.CarEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDialog(showDialog: Boolean, onDismiss: () -> Unit, initialData: CarEntity? = null) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedBrand by remember { mutableStateOf(initialData?.brand ?: "") }
    var carModel by remember { mutableStateOf(initialData?.model ?: "") }
    var year by remember { mutableStateOf(initialData?.year?.toString() ?: "") }
    var price by remember { mutableStateOf(initialData?.price?.toString() ?: "") }
    val coroutineScope = rememberCoroutineScope()

    val isBrandValid = selectedBrand.isNotEmpty()
    val isModelValid = carModel.trim().isNotEmpty()
    val isYearValid = year.toIntOrNull() != null && year.length == 4 && year.toInt() in 1800..2025
    val isPriceValid = price.toDoubleOrNull() != null

    val isFormValid = isModelValid && isYearValid && isPriceValid && isBrandValid

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(stringResource(R.string.add), style = MaterialTheme.typography.titleMedium)

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selectedBrand,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(stringResource(R.string.brand)) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            isError = !isBrandValid,
                            supportingText = {
                                if (!isBrandValid) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(R.string.brand_required),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            CarBrand.entries.forEach { brand ->
                                DropdownMenuItem(
                                    text = { Text(brand.displayName) },
                                    onClick = {
                                        selectedBrand = brand.displayName
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = carModel,
                        onValueChange = { carModel = it },
                        label = { Text(stringResource(R.string.model)) },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !isModelValid,
                        supportingText = {
                            if (!isModelValid) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(R.string.model_required),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = year,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                year = it
                            }
                        },
                        label = { Text(stringResource(R.string.year)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = !isYearValid,
                        supportingText = {
                            if (!isYearValid) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(R.string.year_required),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = price,
                        onValueChange = {
                            if (it.matches(Regex("^\\d*\\.?\\d{0,2}\$"))) {
                                price = it
                            }
                        },
                        label = { Text(stringResource(R.string.price)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = !isPriceValid,
                        supportingText = {
                            if (!isYearValid) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(R.string.price_required),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (initialData != null) {
                            TextButton(onClick = {
                                coroutineScope.launch {
                                    CarRepository.deleteCar(initialData.id)
                                    onDismiss()
                                }
                                Toast.makeText(context, R.string.car_deleted, Toast.LENGTH_SHORT)
                                    .show()
                            }) {
                                Text(stringResource(R.string.delete))
                            }
                            Button(onClick = {
                                val car = CarEntity(
                                    id = initialData.id,
                                    brand = selectedBrand,
                                    model = carModel,
                                    year = year.toInt(),
                                    price = price.toDouble()
                                )

                                coroutineScope.launch {
                                    async {
                                        CarRepository.updateCar(car)
                                    }.await()
                                    onDismiss()
                                }
                                Toast.makeText(
                                    context,
                                    R.string.car_updated,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }, enabled = isFormValid) {
                                Text(stringResource(R.string.update))
                            }

                        } else {
                            TextButton(onClick = onDismiss) {
                                Text(stringResource(R.string.cancel))
                            }
                            Button(onClick = {
                                val car = CarEntity(
                                    brand = selectedBrand,
                                    model = carModel,
                                    year = year.toInt(),
                                    price = price.toDouble()
                                )
                                coroutineScope.launch {
                                    CarRepository.insertCar(car)
                                    onDismiss()
                                }
                                Toast.makeText(context, R.string.car_saved, Toast.LENGTH_SHORT)
                                    .show()
                            }, enabled = isFormValid) {
                                Text(stringResource(R.string.save))
                            }
                        }
                    }
                }
            }
        }
    }
}