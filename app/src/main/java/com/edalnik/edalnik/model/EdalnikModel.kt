package com.edalnik.edalnik.model

class EdalnikModel {
    private val foodList = mutableListOf<FoodItem>()

    fun addFood(food: FoodItem) {
        foodList.add(food)
    }

    fun getAllFood(): List<FoodItem> {
        return foodList.toList()
    }

    fun getTotalCalories(): Int {
        return foodList.sumOf { it.calories }
    }

    fun clearAllFood() {
        foodList.clear()
    }
}