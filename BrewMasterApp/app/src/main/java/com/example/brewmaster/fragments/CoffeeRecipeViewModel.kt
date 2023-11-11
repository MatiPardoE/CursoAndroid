package com.example.brewmaster.fragments

import android.content.Context      //TODO: No deberia estar esto aca con storage deberia cambiar
import androidx.lifecycle.ViewModel
import com.example.brewmaster.database.AppDatabase
import com.example.brewmaster.database.CoffeeRecipeDao
import com.example.brewmaster.entities.CoffeeRecipe

class CoffeeRecipeViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var db: AppDatabase? = null
    private var coffeeRecipeDao: CoffeeRecipeDao? = null

    fun getCoffeeRecipeList(context: Context) : MutableList<CoffeeRecipe?>?{

        db = AppDatabase.getInstance(context)
        coffeeRecipeDao = db?.coffeeRecipeDao()
        return coffeeRecipeDao?.fetchAllCoffeeRecipe()

    }
}