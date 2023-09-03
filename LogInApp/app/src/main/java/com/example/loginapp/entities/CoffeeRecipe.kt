package com.example.loginapp.entities
data class CoffeeRecipe(
    val name: String,
    val description: String,
    val coffeeType: String,
    val grindLevel: String,
    val coffeeToWaterRatio: Double,
    val strength: Int,
    val waterAmountTimePairs: List<WaterAmountTimePair>
)

data class WaterAmountTimePair(
    val waterAmount: Double, // en mililitros
    val brewTime: Int // en segundos
)

