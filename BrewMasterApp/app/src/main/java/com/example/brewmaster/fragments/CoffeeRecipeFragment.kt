package com.example.brewmaster.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brewmaster.R
import com.example.brewmaster.adapters.CoffeeRecipeAdapter
import com.example.brewmaster.database.CoffeeRecipeDao
import com.example.brewmaster.entities.CoffeeRecipe
import com.example.brewmaster.database.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CoffeeRecipeFragment : Fragment() {

    private lateinit var v : View
    private lateinit var rvCoffeeRecipe: RecyclerView
    private lateinit var addButton : FloatingActionButton
    private lateinit var viewModel: CoffeeRecipeViewModel

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

        viewModel = ViewModelProvider(requireActivity())[CoffeeRecipeViewModel::class.java]


        var coffeeRecipeList = viewModel.getCoffeeRecipeList(v.context)

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