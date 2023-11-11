package com.example.brewmaster.fragments

import androidx.lifecycle.ViewModel
import com.example.brewmaster.database.AppDatabase
import com.example.brewmaster.database.CoffeeRecipeDao

class CoffeeRecipeDetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var db: AppDatabase? = null
    private var coffeeRecipeDao: CoffeeRecipeDao? = null
}