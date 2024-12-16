package com.edalnik.edalnik.model
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException


class EdalnikModel() {
    private val foodList: MutableList<FoodItem>

    init {
        Log.d("EdalnikModel", "111111111111111");
        foodList = loadFoodData()
        Log.d("EdalnikModel", "2222222222222222");

    }
    fun addFood(food: FoodItem) {
        foodList.add(food)
    }

    fun getAllFood(): List<FoodItem> {
        return foodList.toList()
    }

    fun clearAllFood() {
        foodList.clear()
    }

    fun loadFoodData(): MutableList<FoodItem> {
        val jsonString = javaClass.getResourceAsStream("/food.json")?.bufferedReader()?.use { it.readText() }
            ?: throw IllegalStateException("Cannot find food.json")

        val gson = Gson()
        val listType = object : TypeToken<List<FoodItem>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
}