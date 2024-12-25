package com.edalnik.edalnik.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.edalnik.edalnik.viewmodel.FoodViewModel
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun DashboardScreen(viewModel: FoodViewModel) {

    val currentCalories by viewModel.currentCalories.collectAsState()
    val targetCalories by viewModel.targetCalories.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center

    ) {
        PieChartView(
            currentCalories,
            targetCalories
        )
        Text(
            text = "Набранные калории: ${currentCalories.toInt()} ккал",
            color = Color.White,
            modifier = Modifier.padding(top = 350.dp)
        )
        Text(
            text = "Дневная норма: ${targetCalories.toInt()} ккал",
            color = Color.White,
            modifier = Modifier.padding(top = 400.dp)
        )
        Text(
            text = if (currentCalories < targetCalories.toInt())
                "Осталось наесть еще: ${(targetCalories- currentCalories).toInt()}"
                else "Норма выполнена",
            color = Color.White,
            modifier = Modifier.padding(top = 450.dp)
        )
    }
}

@Composable
fun PieChartView(
    currentCalories: Float,
    targetCalories: Float
) {
    val target = if (currentCalories>targetCalories) 0F else targetCalories
    PieChart(
        pieChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice(
                    target,
                    Color(0xFF514F4F)
                ),
                PieChartData.Slice(
                    currentCalories,
                    Color(0xFFFFCD74)
                ),
            )
        ),
        modifier = Modifier
            .size(200.dp),
        animation = simpleChartAnimation(),
        sliceDrawer = SimpleSliceDrawer(30F)
    )
}
