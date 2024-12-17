package com.edalnik.edalnik.model
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException


class EdalnikModel() {
    private val foodList: MutableList<FoodItem>
    private val choosedFood: MutableList<FoodItem> = mutableListOf()

    init {
        foodList = loadFoodData()
    }
    fun addFood(food: FoodItem) {
        choosedFood.add(food)
    }

    fun getAllFood(): MutableList<FoodItem> {
        return foodList
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