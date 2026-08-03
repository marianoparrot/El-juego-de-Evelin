package com.evelin.juego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.evelin.juego.ui.screens.MainScreen
import com.evelin.juego.ui.theme.ElJuegoDeEvelinTheme
import com.evelin.juego.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElJuegoDeEvelinTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
