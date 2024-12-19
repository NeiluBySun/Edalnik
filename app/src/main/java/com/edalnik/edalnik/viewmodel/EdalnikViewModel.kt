package com.edalnik.edalnik.viewmodel


import androidx.lifecycle.ViewModel
import com.edalnik.edalnik.model.FoodItem
import com.edalnik.edalnik.model.EdalnikModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class FoodViewModel() : ViewModel() {
    val cause = IllegalStateException("Devide by zero")
    private val repository = EdalnikModel()
    val chosenFood: StateFlow<List<FoodItem>> = repository.chosenFood
    var targetCalories: StateFlow<UInt> = repository.targetCalories

    private val _foodListState = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodListState: StateFlow<List<FoodItem>> = _foodListState

    private val _totalCaloriesState = MutableStateFlow(0)
    val totalCaloriesState: StateFlow<Int> = _totalCaloriesState

    fun addFood(foodItem: FoodItem) {
        foodItem.isChosen = true
        val isExist = repository.isExistOnChosen(foodItem)
        if (!isExist) {
            repository.addFood(foodItem)
        }
    }

    fun getFoodList(): List<FoodItem> {
        return repository.getAllFood().toList()
    }

    fun getCurrentCalories(): UInt {
        return repository.getCurrentCalories()
    }

    fun reduceChosenAmount(foodItem: FoodItem) {
        repository.reduceChosenAmount(foodItem)
    }

    fun calculateCaloriesFraction(): Float {

        val currentCalories = repository.getCurrentCalories()
        if (targetCalories.value == 0U) {
            throw IllegalArgumentException("Target calories shouldn't be equal to zero", cause)
        }
        return currentCalories.toFloat()/targetCalories.value.toFloat()
    }

    fun deleteChosenRow(foodItem: FoodItem) {
        repository.deleteChosenRow(foodItem)
    }
}