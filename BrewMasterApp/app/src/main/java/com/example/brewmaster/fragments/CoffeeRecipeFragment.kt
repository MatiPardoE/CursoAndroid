package com.example.brewmaster.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brewmaster.R
import com.example.brewmaster.adapters.CoffeeRecipeAdapter
import com.example.brewmaster.entities.CoffeeRecipe
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.droidsonroids.gif.GifImageView

class CoffeeRecipeFragment : Fragment() {

    private lateinit var v : View
    private lateinit var rvCoffeeRecipe: RecyclerView
    private lateinit var addButton : FloatingActionButton
    private lateinit var loadingGIF : GifImageView
    private lateinit var viewModel: CoffeeRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_list_coffee_recipe, container, false)

        addButton = v.findViewById(R.id.addButton)
        loadingGIF = v.findViewById(R.id.loadingGIF)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCoffeeRecipe = v.findViewById(R.id.rvCoffeeRecipe)

        viewModel = ViewModelProvider(requireActivity())[CoffeeRecipeViewModel::class.java]

        viewModel.refreshCoffeeRecipeList()

        viewModel.coffeeRecipesList.observe(viewLifecycleOwner, Observer { list ->
            //Estoy enviando al constructor del adapter una funcion anonima se llama Lambda
            val adapter = CoffeeRecipeAdapter(list,::onCoffeeRecipeClicked)

            rvCoffeeRecipe.adapter = adapter

            rvCoffeeRecipe.layoutManager = LinearLayoutManager(context)
        })

        viewModel.progressView.observe(viewLifecycleOwner, Observer { progress ->
            if(progress){
                loadingGIF.visibility = View.VISIBLE
            }else{
                loadingGIF.visibility = View.GONE
            }
        })
    }

    override fun onStart() {
        super.onStart()
        addButton.setOnClickListener{
            val action = CoffeeRecipeFragmentDirections.actionCoffeeRecipeFragmentToCoffeeRecipeDetailFragment("NEW")
            findNavController().navigate(action)
        }
    }

    private fun onCoffeeRecipeClicked(coffeeRecipe: CoffeeRecipe){
        Log.d("Debug","Name: ${coffeeRecipe.name}")
        val action = CoffeeRecipeFragmentDirections.actionCoffeeRecipeFragmentToCoffeeRecipeDetailFragment(coffeeRecipe.id)
        findNavController().navigate(action)

    }

}