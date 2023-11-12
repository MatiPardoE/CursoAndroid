package com.example.brewmaster.fragments

import android.content.Context      //TODO no deberia estar con storage
import androidx.lifecycle.ViewModel
import com.example.brewmaster.database.AppDatabase
import com.example.brewmaster.database.CoffeeRecipeDao
import com.example.brewmaster.entities.CoffeeRecipe

class CoffeeRecipeDetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var db: AppDatabase? = null
    private var coffeeRecipeDao: CoffeeRecipeDao? = null

    fun addCoffeeRecipe(context: Context,coffeeRecipe : CoffeeRecipe){
        val dao = AppDatabase.getInstance(context)?.coffeeRecipeDao()
        dao?.insertCoffeeRecipe(coffeeRecipe)
    }

    fun updateCoffeeRecipe(context: Context,coffeeRecipe : CoffeeRecipe){
        val dao = AppDatabase.getInstance(context)?.coffeeRecipeDao()
        dao?.updateCoffeeRecipe(coffeeRecipe)
    }

    fun deleteCoffeeRecipe(context: Context,coffeeRecipe : CoffeeRecipe){
        val dao = AppDatabase.getInstance(context)?.coffeeRecipeDao()
        dao?.delete(coffeeRecipe)
    }

    fun getCoffeeRecipeByID(context: Context,ID : Int) : CoffeeRecipe? {
        val dao = AppDatabase.getInstance(context)?.coffeeRecipeDao()
        return dao?.fetchCoffeeRecipeById(ID)
    }
}