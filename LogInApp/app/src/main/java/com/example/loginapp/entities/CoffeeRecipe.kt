package com.example.loginapp.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "CoffeeRecipe")
data class CoffeeRecipe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "coffeeType") val coffeeType: String,
    @ColumnInfo(name = "grindLevel") val grindLevel: String,
    @ColumnInfo(name = "coffeeToWaterRatio") val coffeeToWaterRatio: Double,
    @ColumnInfo(name = "strength") val strength: Int
    //@ColumnInfo(name = "url_image") val url_image: String,
    //TODO Ver de agregar la lista de Water Amount
) : Parcelable

/*@Entity(tableName = "WaterAmountTimePair")
@Parcelize
data class WaterAmountTimePairEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "waterAmount")
    val waterAmount: Double,
    @ColumnInfo(name = "brewTime")
    val brewTime: Int,
    @ColumnInfo(name = "recipeId")
    val recipeId: Int // Esta columna se utiliza para establecer una relación con CoffeeRecipe
):Parcelable*/


