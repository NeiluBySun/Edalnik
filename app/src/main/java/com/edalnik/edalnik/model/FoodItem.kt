package com.edalnik.edalnik.model

data class FoodItem(
    val id: Long = 0,
    val name: String,
    val weight: Float,
    val calories: Int,
    val proteins: Double = 0.0,
    val fats: Double = 0.0,
    val carbs: Double = 0.0
)