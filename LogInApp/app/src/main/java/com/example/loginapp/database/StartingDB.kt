package com.example.loginapp.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.loginapp.entities.CoffeeRecipe
import com.example.loginapp.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartingDB(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingDB", "Pre-populating database...")
            fillWithStartingCoffeeRecipe(context)
        }
    }

    /**
     * Pre-populate database with hard-coded Coffee Recipe
     */
    private fun fillWithStartingCoffeeRecipe(context: Context) {
        val coffeeRecipe = listOf(
            CoffeeRecipe(
                id = 0,
                name = "Chemex Coffee",
                description = "A Chemex coffee brewing recipe.",
                coffeeType = "Coffee of your choice",
                grindLevel = "Medium-coarse",
                coffeeToWaterRatio = 1.0 / 16.0, // 1:16 brew ratio
                strength = 2
                //url_image = "https://cdn-icons-png.flaticon.com/128/9246/9246639.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Smile Tiger Coffee Roasters",
                description = "A solid daily driver recipe for a smooth, clean cup.",
                coffeeType = "Medium to medium-light roast (or any coffee you like)",
                grindLevel = "Extra Fine",
                coffeeToWaterRatio = 32.0 / 500.0, // Ratio de café a agua: 32 g de café a 500 g de agua
                strength = 2
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Brewing is for Everyone",
                description = "Perfect for light roasts, makes a sharp, vibrant cup of coffee.",
                coffeeType = "Light roast (recommended)",
                grindLevel = "Medium",
                coffeeToWaterRatio = 30.0 / 480.0, // Ratio de café a agua: 30 g de café a 480 g de agua
                strength = 1
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Paul Ross",
                description = "Perfect for strong coffee lovers, produces a bold and flavorful cup.",
                coffeeType = "Liberica",
                grindLevel = "Medium to medium-coarse",
                coffeeToWaterRatio = 35.0 / 500.0, // Ratio de café a agua: 35 g de café a 500 g de agua
                strength = 4
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Filtru",
                description = "A multi-pour recipe for a deliciously bright cup.",
                coffeeType = "Acidic light roast (recommended)",
                grindLevel = "Medium",
                coffeeToWaterRatio = 25.0 / 340.0, // Ratio de café a agua: 25 g de café a 340 g de agua
                strength = 3
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Strong Hot Coffee",
                description = "A recipe for strong coffee lovers with a 1:12 coffee to water ratio.",
                coffeeType = "Medium to medium-dark roast (recommended)",
                grindLevel = "Medium-coarse",
                coffeeToWaterRatio = 25.0 / 300.0, // Ratio de café a agua: 25 g de café a 300 g de agua (1:12)
                strength = 5
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Blue Bottle Chemex",
                description = "A recipe from Blue Bottle for making large batches of coffee.",
                coffeeType = "Arabica",
                grindLevel = "Coarse",
                coffeeToWaterRatio = 50.0 / 700.0, // Ratio de café a agua: 50 g de café a 700 g de agua
                strength = 3
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Slow Pour",
                description = "A recipe with an extremely slow pour for a smooth, strong cup with enhanced sweetness.",
                coffeeType = "Not specified",
                grindLevel = "Medium",
                coffeeToWaterRatio = 30.0 / 500.0, // Ratio de café a agua: 30 g de café a 500 g de agua
                strength = 5
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Slightly Stronger Coffee",
                description = "A recipe for coffee that's slightly stronger than the standard.",
                coffeeType = "Dark Roast",
                grindLevel = "Medium-Coarse",
                coffeeToWaterRatio = 20.0 / 300.0, // Ratio de café a agua: 20 g de café a 300 g de agua (1:15)
                strength = 2
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "V60 Fast Pour",
                description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
                coffeeType = "Medium or dark roast (recommended)",
                grindLevel = "Medium-coarse",
                coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
                strength = 4,
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Chemex Fast Pour",
                description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
                coffeeType = "Medium or dark roast (recommended)",
                grindLevel = "Extra Fine",
                coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
                strength = 4,
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Chemex Strong",
                description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
                coffeeType = "Medium or dark roast (recommended)",
                grindLevel = "Fine",
                coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
                strength = 4,
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                id = 0,
                name = "Chemex calm",
                description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
                coffeeType = "Medium or dark roast (recommended)",
                grindLevel = "Medium-coarse",
                coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
                strength = 4,
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            )
        )
        val usersList = listOf(
            User(0,"User1","1"),
            User(0,"User2","2"),
            User(0,"User3","3"))
        val dao = AppDatabase.getInstance(context)?.coffeeRecipeDao()
        val daoUser = AppDatabase.getInstance(context)?.userDao()

        coffeeRecipe.forEach {
            dao?.insertCoffeeRecipe(it)
        }
        usersList.forEach {
            daoUser?.insertUser(it)
        }
    }
}
