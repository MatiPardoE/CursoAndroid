package com.example.loginapp.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.loginapp.R

class CoffeeRecipeDetailFragment : Fragment() {

    lateinit var v : View
    lateinit var txtNameCoffeeRecipe : TextView
    lateinit var textDescriptionCoffeeRecipe : TextView
    lateinit var textCoffeeType : TextView
    lateinit var textGrindLevel : TextView
    lateinit var seekBarStrength : SeekBar
    lateinit var textRatio : TextView
    lateinit var listWater_Time : ListView
    private val args : CoffeeRecipeDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_coffee_recipe_detail, container, false)

        txtNameCoffeeRecipe = v.findViewById(R.id.txtNameCoffeeRecipe)
        textDescriptionCoffeeRecipe = v.findViewById(R.id.textDescriptionCoffeeRecipe)
        textCoffeeType = v.findViewById(R.id.textCoffeeType)
        textDescriptionCoffeeRecipe = v.findViewById(R.id.textDescriptionCoffeeRecipe)
        textGrindLevel = v.findViewById(R.id.textGrindLevel)
        textRatio = v.findViewById(R.id.textRatio)
        seekBarStrength = v.findViewById(R.id.seekBarStrength)
        listWater_Time = v.findViewById(R.id.listWater_Time)

        seekBarStrength.isEnabled = false
        return v
    }

    override fun onStart() {
        super.onStart()
        txtNameCoffeeRecipe.text = args.coffeeRecipe.name
        textDescriptionCoffeeRecipe.text = "Description: " + args.coffeeRecipe.description
        textCoffeeType.text = "Coffee Type: " + args.coffeeRecipe.coffeeType
        textGrindLevel.text = "Grind Level: " + args.coffeeRecipe.grindLevel
        seekBarStrength.progress = args.coffeeRecipe.strength-1
        textRatio.text = "1 : " + String.format("%.3f", 1.0 / args.coffeeRecipe.coffeeToWaterRatio).toFloat().toString()

        //TODO: Agregar un nuevo Recycled view para agregar los pasos

    }


}