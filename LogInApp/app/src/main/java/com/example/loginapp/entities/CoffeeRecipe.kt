package com.example.loginapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoffeeRecipe(
    val name: String,
    val description: String,
    val coffeeType: String,
    val grindLevel: String,
    val coffeeToWaterRatio: Double,
    val strength: Int,
    val waterAmountTimePairs: List<WaterAmountTimePair>
) : Parcelable

@Parcelize
data class WaterAmountTimePair(
    val waterAmount: Double, // en mililitros
    val brewTime: Int // en segundos
):Parcelable


