package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.adapters.CoffeeRecipeAdapter
import com.example.loginapp.entities.CoffeeRecipe
import com.example.loginapp.entities.WaterAmountTimePair

class CoffeeRecipeFragment : Fragment() {

    private lateinit var v : View
    private lateinit var rvCoffeeRecipe: RecyclerView
    private var coffeeRecipeList = listOf(
        CoffeeRecipe(
            name = "Chemex Coffee",
            description = "A Chemex coffee brewing recipe.",
            coffeeType = "Coffee of your choice",
            grindLevel = "Medium-coarse",
            coffeeToWaterRatio = 1.0 / 16.0, // 1:16 brew ratio
            strength = 2,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(90.0, 30), // At :30, pour 90g of water, brew for 30 seconds
                WaterAmountTimePair(210.0, 60), // At 1:30, pour 210g of water, brew for 60 seconds
                WaterAmountTimePair(210.0, 60)  // At 2:30, pour 210g of water, brew for 60 seconds
            )),
        CoffeeRecipe(
            name = "Smile Tiger Coffee Roasters",
            description = "A solid daily driver recipe for a smooth, clean cup.",
            coffeeType = "Medium to medium-light roast (or any coffee you like)",
            grindLevel = "Not specified",
            coffeeToWaterRatio = 32.0 / 500.0, // Ratio de café a agua: 32 g de café a 500 g de agua
            strength = 2,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(60.0, 30), // Vierte 60 g de agua y revuelve suavemente después de 30 segundos.
                WaterAmountTimePair(190.0, 45), // A los 1:15 minutos, vierte hasta 250 g de agua.
                WaterAmountTimePair(50.0, 15)   // A los 2:00 minutos, vierte los últimos 50 g de agua hasta un total de 500 g.
            )),
        CoffeeRecipe(
            name = "Brewing is for Everyone",
            description = "Perfect for light roasts, makes a sharp, vibrant cup of coffee.",
            coffeeType = "Light roast (recommended)",
            grindLevel = "Medium",
            coffeeToWaterRatio = 30.0 / 480.0, // Ratio de café a agua: 30 g de café a 480 g de agua
            strength = 1,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(60.0, 30), // Bloom: Vierte 60 g de agua, revuelve suavemente y espera 30 segundos.
                WaterAmountTimePair(420.0, 0)  // Vierte lentamente hasta un total de 480 g de agua.
            )
        ),
        CoffeeRecipe(
            name = "Paul Ross",
            description = "Perfect for strong coffee lovers, produces a bold and flavorful cup.",
            coffeeType = "Medium to medium-coarse grind (recommended)",
            grindLevel = "Medium to medium-coarse",
            coffeeToWaterRatio = 35.0 / 500.0, // Ratio de café a agua: 35 g de café a 500 g de agua
            strength = 4,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(70.0, 30), // Bloom: Vierte 70 g de agua justo después de hervir, revuelve la mezcla y espera 30 segundos.
                WaterAmountTimePair(430.0, 0)  // Vierte los 430 g de agua restantes y revuelve la mezcla nuevamente.
            )
        ),
        CoffeeRecipe(
            name = "Filtru",
            description = "A multi-pour recipe for a deliciously bright cup.",
            coffeeType = "Acidic light roast (recommended)",
            grindLevel = "Medium",
            coffeeToWaterRatio = 25.0 / 340.0, // Ratio de café a agua: 25 g de café a 340 g de agua
            strength = 3,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(50.0, 30),   // Bloom: Vierte 50 g de agua, revuelve los granos para asegurar un mojado uniforme y espera 30 segundos.
                WaterAmountTimePair(130.0, 0),   // Vierte otros 130 g de agua.
                WaterAmountTimePair(160.0, 0)    // Vierte lentamente otros 160 g de agua.
            )
        ),
        CoffeeRecipe(
            name = "Strong Hot Coffee",
            description = "A recipe for strong coffee lovers with a 1:12 coffee to water ratio.",
            coffeeType = "Medium to medium-dark roast (recommended)",
            grindLevel = "Medium-coarse",
            coffeeToWaterRatio = 25.0 / 300.0, // Ratio de café a agua: 25 g de café a 300 g de agua (1:12)
            strength = 5,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(30.0, 45),   // Vierte 30 g de agua y revuelve suavemente después de 45 segundos.
                WaterAmountTimePair(270.0, 105)  // Vierte el agua restante lentamente, apuntando a un tiempo total de elaboración de al menos 2:30 minutos.
            )
        ),
        CoffeeRecipe(
            name = "Blue Bottle Chemex",
            description = "A recipe from Blue Bottle for making large batches of coffee.",
            coffeeType = "Not specified",
            grindLevel = "Not specified",
            coffeeToWaterRatio = 50.0 / 700.0, // Ratio de café a agua: 50 g de café a 700 g de agua
            strength = 3,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(100.0, 45),   // Vierte 100 g de agua en un patrón concéntrico en espiral desde el centro. Espera 45 segundos.
                WaterAmountTimePair(200.0, 0),    // Vierte 200 g de agua en un patrón circular y espera a que el agua se drene.
                WaterAmountTimePair(200.0, 0)     // Vierte otros 200 g de agua.
            )
        ),
        CoffeeRecipe(
            name = "Slow Pour",
            description = "A recipe with an extremely slow pour for a smooth, strong cup with enhanced sweetness.",
            coffeeType = "Not specified",
            grindLevel = "Medium",
            coffeeToWaterRatio = 30.0 / 500.0, // Ratio de café a agua: 30 g de café a 500 g de agua
            strength = 5,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(60.0, 45),   // Vierte suficiente agua para mojar uniformemente los granos, aproximadamente 60 g. Espera 45 segundos.
                WaterAmountTimePair(440.0, 135)  // Vierte el resto del agua muy lentamente. Apunta a terminar de verter a los 3 minutos.
            )
        ),
        CoffeeRecipe(
            name = "Slightly Stronger Coffee",
            description = "A recipe for coffee that's slightly stronger than the standard.",
            coffeeType = "Not specified",
            grindLevel = "Not specified",
            coffeeToWaterRatio = 20.0 / 300.0, // Ratio de café a agua: 20 g de café a 300 g de agua (1:15)
            strength = 2,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(40.0, 45),  // Bloom: Vierte 40 g de agua uniformemente sobre los granos. Espera de 30 a 45 segundos.
                WaterAmountTimePair(260.0, 135) // Vierte el resto del agua en círculos concéntricos lentamente. El tiempo total de elaboración debe ser de 3:00 minutos.
            )
        ),
        CoffeeRecipe(
            name = "Single Fast Pour",
            description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
            coffeeType = "Medium or dark roast (recommended)",
            grindLevel = "Medium-coarse",
            coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
            strength = 4,
            waterAmountTimePairs = listOf(
                WaterAmountTimePair(30.0, 45),   // Vierte suficiente agua para cubrir el café y revuelve. Espera de 30 a 45 segundos.
                WaterAmountTimePair(310.0, 60)   // Vierte el resto del agua rápidamente. Termina de verter en alrededor de 1 minuto.
            )
        )

    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_list_coffee_recipe, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCoffeeRecipe = v.findViewById(R.id.rvCoffeeRecipe)

        val adapter = CoffeeRecipeAdapter(coffeeRecipeList)
        rvCoffeeRecipe.adapter = adapter

        rvCoffeeRecipe.layoutManager = LinearLayoutManager(context)

    }

}