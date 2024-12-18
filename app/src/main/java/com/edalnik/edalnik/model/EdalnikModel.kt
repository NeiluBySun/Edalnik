package com.edalnik.edalnik.model
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException


class EdalnikModel() {
    private val foodList: MutableList<FoodItem>
    private val chosenFood: MutableList<FoodItem> = mutableListOf()

    init {
        foodList = loadFoodData()
    }
    fun addFood(food: FoodItem) {
        food.amount++
        chosenFood.add(food)
    }

    fun reduceChosenAmount(food: FoodItem) {
        chosenFood.forEachIndexed { index, foodItem ->
            if(foodItem.name == food.name)
            {
                chosenFood[index].amount--
                if (chosenFood[index].amount == 0) {
                    deleteChosenRow(foodItem, index)
                }
                return
            }
        }
    }

    fun deleteChosenRow(food: FoodItem, supIndex: Int = -1) {
        if (supIndex != -1) {
            chosenFood.removeAt(supIndex)
            return
        }
        chosenFood.forEachIndexed { index, foodItem ->
            if (foodItem.name == food.name) {
                chosenFood.removeAt(index)
                return
            }
        }
    }

    fun getAllFood(): MutableList<FoodItem> {
        return foodList
    }

    fun getChosenFood(): MutableList<FoodItem> {
        return chosenFood
    }

    fun isExistOnChosen(food: FoodItem): Boolean {
        chosenFood.forEachIndexed { index, foodItem ->
            if(foodItem.name == food.name)
            {
                chosenFood[index].amount++
                return true
            }
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