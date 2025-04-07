package com.manuelrurda.mod6_practica1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.manuelrurda.mod6_practica1.ui.composables.MainScreen
import com.manuelrurda.mod6_practica1.ui.theme.Mod6_Practica1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            Mod6_Practica1Theme {
                MainScreen()
            }
        }
    }
}