package com.edalnik.edalnik.model
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.FileNotFoundException


class EdalnikModel() {
    private val foodList: MutableList<FoodItem>
    private val _chosenFood = MutableStateFlow<List<FoodItem>>(emptyList())
    private var currentCalories:Float =  0F
    var targetCalories = MutableStateFlow<Float>(2300F)
    val chosenFood: StateFlow<List<FoodItem>> = _chosenFood.asStateFlow()

    init {
        foodList = loadFoodData()
    }
    fun addFood(food: FoodItem) {
        food.amount = MutableStateFlow(1)
        _chosenFood.value += food
    }
    fun reduceChosenAmount(food: FoodItem) {
        _chosenFood.value.forEachIndexed { index, foodItem ->
            if(foodItem.name == food.name)
            {
                _chosenFood.value[index].amount.value--
                if (_chosenFood.value[index].amount.value == 0) {
                    deleteChosenRow(foodItem, index)
                }
                return
            }
        }
    }

    fun calculateCalories() {
        var total = 0U
        _chosenFood.value.forEach { foodItem ->
            total += foodItem
                .calories
                .split(" ")[0].toUInt()
        }
        currentCalories = total
    }

    fun deleteChosenRow(food: FoodItem, supIndex: Int = -1) {
        if (supIndex != -1) {
            if (supIndex in _chosenFood.value.indices) {
                val mutableList = _chosenFood.value.toMutableList()
                mutableList.removeAt(supIndex)
                _chosenFood.value = mutableList
            }
            return

        }
       _chosenFood.value.forEachIndexed { index, foodItem ->
            if (foodItem.name == food.name) {
                _chosenFood.value -= foodItem
                return
            }
        }
    }

    fun getAllFood(): MutableList<FoodItem> {
        return foodList
    }

    fun getCurrentCalories(): UInt {
        return currentCalories
    }

    fun setTargetCalories(target: UInt) {
        targetCalories = MutableStateFlow(target)
    }

    fun isExistOnChosen(food: FoodItem): Boolean {
        _chosenFood.value.find {it.name == food.name}?.let { foodItem ->
            foodItem.amount.value +=1
            return true
        }
        return false
    }

    fun clearAllFood() {
        foodList.clear()
    }

    private fun loadFoodData(): MutableList<FoodItem> {
        val inputStream = javaClass.classLoader?.getResourceAsStream("assets/food.json")
            ?: throw IllegalStateException("Cannot find food.json")

        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val listType = object : TypeToken<MutableList<FoodItem>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

}