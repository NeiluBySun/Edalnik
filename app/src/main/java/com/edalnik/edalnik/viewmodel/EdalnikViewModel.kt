package com.edalnik.edalnik.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import com.edalnik.edalnik.model.ActivityLevel
import com.edalnik.edalnik.model.FoodItem
import com.edalnik.edalnik.model.EdalnikModel
import com.edalnik.edalnik.model.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class FoodViewModel(context: Context) : ViewModel() {
    val cause = IllegalStateException("Devided by zero")
    private val repository = EdalnikModel(context)
    val chosenFood: StateFlow<List<FoodItem>> = repository.chosenFood
    var targetCalories: StateFlow<Float> = repository.targetCalories
    var currentCalories: StateFlow<Float> = repository.currentCalories
    var customTargetCalories: StateFlow<Float> = repository.customTargetCalories
    var gender: StateFlow<Gender> = repository.gender
    var height: StateFlow<Int> = repository.height
    var weight: StateFlow<Int> = repository.weight
    var age: StateFlow<Int> = repository.age
    var activityLevel: StateFlow<ActivityLevel> = repository.activityLevel



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

    fun calculateCaloriesFraction(): Float {

        if (targetCalories.value == 0F) {
            throw IllegalArgumentException("Target calories shouldn't be equal to zero", cause)
        }
        return currentCalories.value/targetCalories.value
    }

    fun deleteChosenRow(foodItem: FoodItem) {
        repository.deleteChosenRow(foodItem)
    }

    fun setGender(newGender: Gender) {
       repository.setGender(newGender)
    }

    fun setHeight(newHeight: Int) {
        repository.setHeight(newHeight)
    }

    fun setWeight(newWeight: Int) {
        repository.setWeight(newWeight)
    }

    fun setAge(newAge: Int) {
        repository.setAge(newAge)
    }

    fun setActivityLevel(level: ActivityLevel) {
        repository.setActivityLevel(level)
    }

    fun setCustomTargetCalories(calories: Float) {
        repository.setCustomTargetCalories(calories)
    }

}