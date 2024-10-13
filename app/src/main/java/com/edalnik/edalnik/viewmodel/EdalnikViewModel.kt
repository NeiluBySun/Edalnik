package com.edalnik.edalnik.viewmodel

import androidx.lifecycle.ViewModel
import com.edalnik.edalnik.model.FoodItem
import com.edalnik.edalnik.model.EdalnikModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FoodViewModel : ViewModel() {

    private val repository = EdalnikModel()

    private val _foodListState = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodListState: StateFlow<List<FoodItem>> = _foodListState

    private val _totalCaloriesState = MutableStateFlow(0)
    val totalCaloriesState: StateFlow<Int> = _totalCaloriesState

    fun addFood(name: FoodItem, calories: Int, proteins: Double, fats: Double, carbs: Double) {
        repository.addFood(name)
        _foodListState.value = repository.getAllFood()
        _totalCaloriesState.value = repository.getTotalCalories()
    }

    fun clearAllFood() {
        repository.clearAllFood()
        _foodListState.value = emptyList()
        _totalCaloriesState.value = 0
    }
}