package com.edalnik.edalnik.model

import kotlinx.coroutines.flow.MutableStateFlow

data class FoodItem(
    val name: String,
    val calories: String,
    val proteins: String = "0.0",
    val fats: String = "0.0",
    val carbs: String = "0.0",
    var isChosen: Boolean = false,
    var amount: MutableStateFlow<Int> = MutableStateFlow(1)
)


data class SerializableFoodItem(
    val name: String,
    val calories: String,
    val proteins: String = "0.0",
    val fats: String = "0.0",
    val carbs: String = "0.0",
    var isChosen: Boolean = false,
    var amount: Int

)