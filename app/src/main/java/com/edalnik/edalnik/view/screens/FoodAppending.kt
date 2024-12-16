package com.edalnik.edalnik.view.screens
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.edalnik.edalnik.viewmodel.FoodViewModel


@Composable
fun FoodAppendingScreen(viewModel: FoodViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center

    ) {
        Text(text = "Home Screen")
    }
}



