package com.edalnik.edalnik.viewmodel

import androidx.lifecycle.ViewModel
import com.edalnik.edalnik.model.FoodItem
import com.edalnik.edalnik.model.EdalnikModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FoodViewModel() : ViewModel() {

    private val repository = EdalnikModel()
    val chosenFood: StateFlow<List<FoodItem>> = repository.chosenFood

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


    fun reduceChosenAmount(foodItem: FoodItem) {
        repository.reduceChosenAmount(foodItem)
    }

    fun deleteChosenRow(foodItem: FoodItem) {
        repository.deleteChosenRow(foodItem)
    }
}