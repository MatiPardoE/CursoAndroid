package com.example.brewmaster.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brewmaster.entities.CoffeeRecipe

@Dao
interface CoffeeRecipeDao {
    @Query("SELECT * FROM CoffeeRecipe ORDER BY id")
    fun fetchAllCoffeeRecipe(): MutableList<CoffeeRecipe?>?

    @Query("SELECT * FROM CoffeeRecipe WHERE id = :id")
    fun fetchCoffeeRecipeById(id: Int): CoffeeRecipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoffeeRecipe(coffeeRecipe: CoffeeRecipe)

    @Update
    fun updateCoffeeRecipe(coffeeRecipe: CoffeeRecipe) //Lo hace en funcion de la primary key

    @Delete
    fun delete(coffeeRecipe: CoffeeRecipe)
}