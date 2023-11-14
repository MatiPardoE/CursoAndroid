package com.example.brewmaster.fragments

import android.content.Context      //TODO no deberia estar con storage
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewmaster.database.FirestoreDataSource
import com.example.brewmaster.entities.CoffeeBean
import com.example.brewmaster.entities.CoffeeRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CoffeeRecipeDetailViewModel : ViewModel() {

    private val fireStoreDB = FirestoreDataSource()

    //LiveData
    private val _coffeeRecipe: MutableLiveData<CoffeeRecipe> = MutableLiveData()
    val coffeeRecipe: LiveData<CoffeeRecipe> get() = _coffeeRecipe

    private val _coffeeBeans: MutableLiveData<MutableList<CoffeeBean>> = MutableLiveData()
    val coffeeBeans: LiveData<MutableList<CoffeeBean>> get() = _coffeeBeans

    private val _newCoffeeRecipeFlag: MutableLiveData<Boolean> = MutableLiveData()
    val newCoffeeRecipeFlag: LiveData<Boolean> get() = _newCoffeeRecipeFlag

    private val _deletedCoffeeRecipeFlag: MutableLiveData<Boolean> = MutableLiveData()
    val deletedCoffeeRecipeFlag: LiveData<Boolean> get() = _deletedCoffeeRecipeFlag

    private val _updateCoffeeRecipeFlag: MutableLiveData<Boolean> = MutableLiveData()
    val updateCoffeeRecipeFlag: LiveData<Boolean> get() = _updateCoffeeRecipeFlag

    private val _progressView: MutableLiveData<Boolean> = MutableLiveData()
    val progressView: LiveData<Boolean> get() = _progressView


    fun addCoffeeRecipe(coffeeRecipe: CoffeeRecipe) {
        viewModelScope.launch(Dispatchers.Main) {
            _progressView.value = true
            _newCoffeeRecipeFlag.value = fireStoreDB.addCoffeeRecipe(coffeeRecipe)
            _progressView.value = false
            _newCoffeeRecipeFlag.value = false
        }
    }

    fun updateCoffeeRecipe(coffeeRecipe: CoffeeRecipe) {
        viewModelScope.launch(Dispatchers.Main) {
            _progressView.value = true
            _updateCoffeeRecipeFlag.value = fireStoreDB.updateCoffeeRecipe(coffeeRecipe)
            _progressView.value = false
            _updateCoffeeRecipeFlag.value = false
        }
    }

    fun deleteCoffeeRecipe(coffeeRecipeID: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _progressView.value = true
            _deletedCoffeeRecipeFlag.value = fireStoreDB.deleteCoffeeRecipeByID(coffeeRecipeID)
            _deletedCoffeeRecipeFlag.value = false
        }
    }

    fun getCoffeeRecipeByID(coffeeRecipeID: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _progressView.value = true

            _coffeeBeans.value = fireStoreDB.getCoffeeBeans()

            _coffeeRecipe.value = fireStoreDB.getCoffeeRecipeByID(coffeeRecipeID)
            // Finalizar la tarea y actualizar el progressView
            _progressView.value = false
        }
    }

    fun getCoffeeBeans(){
        viewModelScope.launch(Dispatchers.Main) {
            _progressView.value = true

            _coffeeBeans.value = fireStoreDB.getCoffeeBeans()

            // Finalizar la tarea y actualizar el progressView
            _progressView.value = false
        }
    }
    fun disableLoading(){
        _progressView.value = false
    }
}