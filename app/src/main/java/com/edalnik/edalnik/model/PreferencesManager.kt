package com.edalnik.edalnik.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.Serializer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.io.InputStream
import kotlin.reflect.typeOf

class FoodItemStorage(context: Context) {
    private val gson = Gson()
    private val file: File = File(context.getExternalFilesDir(null), "aboba.json")

    fun saveFoodItems(foodItems: List<FoodItem>) {

        val json = gson.toJson(foodItems.map {it.toSerializable()})
        Log.d("СОХРАНЯЛКА", json.toString())
        file.writeText(json)
    }
    fun loadFoodItems(): List<FoodItem> {
        if (!file.exists()) {
            return emptyList()
        }
        val json = file.readText()
        val type = object : TypeToken<List<SerializableFoodItem>>() {}.type
        val foodList = gson.fromJson<List<SerializableFoodItem>>(json, type)
        val list = foodList.map {it.toFoodItem()}

        return list
    }

    fun FoodItem.toSerializable(): SerializableFoodItem {
        return SerializableFoodItem(
            name = name,
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs,
            isChosen = isChosen,
            amount = amount.value
        )
    }

    fun SerializableFoodItem.toFoodItem(): FoodItem {
        return FoodItem(
            name = name,
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs,
            isChosen = isChosen,
            amount = MutableStateFlow(amount)
        )
    }
}