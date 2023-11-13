package com.example.brewmaster.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoffeeRecipe(
    val id: String = "", // Valor predeterminado para auto-generación
    val name: String = "",
    val description: String = "",
    val coffeeType: String = "",
    val grindLevel: String = "",
    val coffeeToWaterRatio: Double = 0.0,
    val strength: Int = 0,
    val barcodeCoffeeBean: String = "5430000838665"
    //@ColumnInfo(name = "url_image") val url_image: String = "",
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


