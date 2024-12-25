package com.edalnik.edalnik.model
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import java.io.File

enum class Gender {
    MALE, FEMALE
}

enum class ActivityLevel {
    SEDENTARY,
    LIGHT,
    MODERATE,
    ACTIVE,
    VERY_ACTIVE
}

class EdalnikModel(private val context: Context) {
    private val foodList: MutableList<FoodItem>
    private val _chosenFood: MutableStateFlow<List<FoodItem>>
    var currentCalories = MutableStateFlow(0F)
    var targetCalories = MutableStateFlow(2300F)
    val chosenFood: StateFlow<List<FoodItem>>
    private val loadManager: FoodItemStorage

    private val _customTargetCalories = MutableStateFlow(0F)
    val customTargetCalories: StateFlow<Float> = _customTargetCalories.asStateFlow()

    private val _gender = MutableStateFlow<Gender>(Gender.MALE)
    val gender: StateFlow<Gender> = _gender.asStateFlow()

    private val _height = MutableStateFlow(170)
    val height: StateFlow<Int> = _height.asStateFlow()

    private val _weight = MutableStateFlow(70)
    val weight: StateFlow<Int> = _weight.asStateFlow()

    private val _age = MutableStateFlow(25)
    val age: StateFlow<Int> = _age.asStateFlow()

    private val _activityLevel = MutableStateFlow<ActivityLevel>(ActivityLevel.MODERATE)
    val activityLevel: StateFlow<ActivityLevel> = _activityLevel.asStateFlow()


    init {
        foodList = loadFoodData()
        loadManager = FoodItemStorage(context)
        Log.d("MODEL", "1")
        val loaded = loadManager.loadFoodItems()
        _chosenFood = MutableStateFlow(loaded)

        chosenFood = _chosenFood.asStateFlow()
        calculateCalories()
        Log.d("MODEL", chosenFood.value.toString())

    }

    fun addFood(food: FoodItem) {
        food.amount = MutableStateFlow(1)
        _chosenFood.value += food
        calculateCalories()
        loadManager.saveFoodItems(chosenFood.value)
    }
    fun reduceChosenAmount(food: FoodItem) {
        _chosenFood.value.forEachIndexed { index, foodItem ->
            if(foodItem.name == food.name)
            {
                _chosenFood.value[index].amount.value--
                if (_chosenFood.value[index].amount.value == 0) {
                    deleteChosenRow(foodItem, index)
                }
                calculateCalories()
                loadManager.saveFoodItems(chosenFood.value)

                return
            }
        }
    }

    private fun calculateCalories() {
        var total = 0F
        chosenFood.value.forEach { foodItem ->
            total += (foodItem
                .calories
                .split(" ")[0].toFloat() * foodItem.amount.value.toFloat())

        }
        currentCalories.value = total

    }

    fun deleteChosenRow(food: FoodItem, supIndex: Int = -1) {
        if (supIndex != -1) {
            if (supIndex in _chosenFood.value.indices) {
                val mutableList = _chosenFood.value.toMutableList()
                mutableList.removeAt(supIndex)
                _chosenFood.value = mutableList
            }
            calculateCalories()
            loadManager.saveFoodItems(chosenFood.value)

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


    fun setTargetCalories(target: Float) {
        targetCalories.value = target
    }

    fun isExistOnChosen(food: FoodItem): Boolean {
        _chosenFood.value.find {it.name == food.name}?.let { foodItem ->
            foodItem.amount.value += 1
            calculateCalories()
            loadManager.saveFoodItems(chosenFood.value)
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

    fun setGender(newGender: Gender) {
        _gender.value = newGender
        calculateTargetCalories()
    }

    fun setHeight(newHeight: Int) {
        _height.value = newHeight
        calculateTargetCalories()
    }

    fun setWeight(newWeight: Int) {
        _weight.value = newWeight
        calculateTargetCalories()
    }

    fun setAge(newAge: Int) {
        _age.value = newAge
        calculateTargetCalories()
    }

    fun setActivityLevel(level: ActivityLevel) {
        _activityLevel.value = level
        calculateTargetCalories()
    }

    fun setCustomTargetCalories(calories: Float) {
        _customTargetCalories.value = calories
    }

    private fun calculateTargetCalories() {
        val bmr = when (_gender.value) {
            Gender.MALE -> {
                88.362 + (13.397 * _weight.value) + (4.799 * _height.value) - (5.677 * _age.value)
            }
            Gender.FEMALE -> {
                447.593 + (9.247 * _weight.value) + (3.098 * _height.value) - (4.330 * _age.value)
            }
        }

        val calories = when (_activityLevel.value) {
            ActivityLevel.SEDENTARY -> bmr * 1.2
            ActivityLevel.LIGHT -> bmr * 1.375
            ActivityLevel.MODERATE -> bmr * 1.55
            ActivityLevel.ACTIVE -> bmr * 1.725
            ActivityLevel.VERY_ACTIVE -> bmr * 1.9
        }

        targetCalories.value = calories.toFloat()
    }


}