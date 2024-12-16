package com.edalnik.edalnik

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.edalnik.edalnik.ui.theme.EdalnikTheme
import com.edalnik.edalnik.viewmodel.FoodViewModel



class MainActivity : ComponentActivity() {
    private lateinit var viewModel: FoodViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        setContent {
            EdalnikTheme() {
                MainScreen(viewModel)
            }
        }
    }
}