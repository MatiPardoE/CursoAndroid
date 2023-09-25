package com.example.loginapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.adapters.CoffeeRecipeAdapter
import com.example.loginapp.database.CoffeeRecipeDao
import com.example.loginapp.entities.CoffeeRecipe
import com.example.loginapp.database.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CoffeeRecipeFragment : Fragment() {

    private lateinit var v : View
    private lateinit var rvCoffeeRecipe: RecyclerView
    private lateinit var addButton : FloatingActionButton
    private var db: AppDatabase? = null
    private var coffeeRecipeDao: CoffeeRecipeDao? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_list_coffee_recipe, container, false)

        addButton = v.findViewById(R.id.addButton)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(v.context)
        coffeeRecipeDao = db?.coffeeRecipeDao()


        // Dummy call to pre-populate db
        var coffeeRecipeList = coffeeRecipeDao?.fetchAllCoffeeRecipe()

        rvCoffeeRecipe = v.findViewById(R.id.rvCoffeeRecipe)

        //Estoy enviando al constructor del adapter una funcion anonima se llama Lambda
        val adapter = CoffeeRecipeAdapter(coffeeRecipeList,::onCoffeeRecipeClicked)

        rvCoffeeRecipe.adapter = adapter

        rvCoffeeRecipe.layoutManager = LinearLayoutManager(context)

    }

    override fun onStart() {
        super.onStart()
        addButton.setOnClickListener{
            val action = CoffeeRecipeFragmentDirections.actionCoffeeRecipeFragmentToCoffeeRecipeDetailFragment(-1)
            findNavController().navigate(action)
        }
    }

    private fun onCoffeeRecipeClicked(coffeeRecipe: CoffeeRecipe){
        Log.d("Debug","Name: ${coffeeRecipe.name}")
        val action = CoffeeRecipeFragmentDirections.actionCoffeeRecipeFragmentToCoffeeRecipeDetailFragment(coffeeRecipe.id)
        findNavController().navigate(action)

    }

}