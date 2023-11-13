package com.example.brewmaster.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewmaster.database.FirestoreDataSource
import com.example.brewmaster.entities.CoffeeRecipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CoffeeRecipeViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val fireStoreDB = FirestoreDataSource()

    //LiveData
    private val _coffeeRecipesList : MutableLiveData<MutableList<CoffeeRecipe>> = MutableLiveData()
    val coffeeRecipesList : LiveData <MutableList<CoffeeRecipe>> get() = _coffeeRecipesList

    private val _progressView : MutableLiveData<Boolean> = MutableLiveData()
    val progressView : LiveData<Boolean> get() = _progressView

    fun refreshCoffeeRecipeList(){
        viewModelScope.launch(Dispatchers.Main) {
            _progressView.value = true
            _coffeeRecipesList.value = fireStoreDB.getAllRecipes()
            _progressView.value = false
        }
    }
}