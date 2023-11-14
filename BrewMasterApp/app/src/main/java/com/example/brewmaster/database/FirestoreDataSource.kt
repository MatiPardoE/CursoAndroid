package com.example.brewmaster.database

import android.content.ContentValues.TAG
import android.util.Log
import com.example.brewmaster.entities.CoffeeBean
import com.example.brewmaster.entities.CoffeeRecipe
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSource {

    // Obtén una instancia de FirebaseFirestore
    private val db = Firebase.firestore
    private val usersCollection = db.collection("Users")
    private val coffeeBeansCollection = db.collection("CoffeeBeans")

    suspend fun addUser(UID: String, email: String) {
        val coffeeRecipe = listOf(
            CoffeeRecipe(
                name = "Chemex Coffee",
                description = "A Chemex coffee brewing recipe.",
                coffeeType = "Coffee of your choice",
                grindLevel = "Medium-coarse",
                coffeeToWaterRatio = 1.0 / 16.0, // 1:16 brew ratio
                strength = 2
                //url_image = "https://cdn-icons-png.flaticon.com/128/9246/9246639.png"
            ),
            CoffeeRecipe(
                name = "Smile Tiger Coffee Roasters",
                description = "A solid daily driver recipe for a smooth, clean cup.",
                coffeeType = "Medium to medium-light roast (or any coffee you like)",
                grindLevel = "Extra Fine",
                coffeeToWaterRatio = 32.0 / 500.0, // Ratio de café a agua: 32 g de café a 500 g de agua
                strength = 2
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Brewing is for Everyone",
                description = "Perfect for light roasts, makes a sharp, vibrant cup of coffee.",
                coffeeType = "Light roast (recommended)",
                grindLevel = "Medium",
                coffeeToWaterRatio = 30.0 / 480.0, // Ratio de café a agua: 30 g de café a 480 g de agua
                strength = 1
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Paul Ross",
                description = "Perfect for strong coffee lovers, produces a bold and flavorful cup.",
                coffeeType = "Liberica",
                grindLevel = "Medium to medium-coarse",
                coffeeToWaterRatio = 35.0 / 500.0, // Ratio de café a agua: 35 g de café a 500 g de agua
                strength = 4
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Filtru",
                description = "A multi-pour recipe for a deliciously bright cup.",
                coffeeType = "Acidic light roast (recommended)",
                grindLevel = "Medium",
                coffeeToWaterRatio = 25.0 / 340.0, // Ratio de café a agua: 25 g de café a 340 g de agua
                strength = 3
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Strong Hot Coffee",
                description = "A recipe for strong coffee lovers with a 1:12 coffee to water ratio.",
                coffeeType = "Medium to medium-dark roast (recommended)",
                grindLevel = "Medium-coarse",
                coffeeToWaterRatio = 25.0 / 300.0, // Ratio de café a agua: 25 g de café a 300 g de agua (1:12)
                strength = 5
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Blue Bottle Chemex",
                description = "A recipe from Blue Bottle for making large batches of coffee.",
                coffeeType = "Arabica",
                grindLevel = "Coarse",
                coffeeToWaterRatio = 50.0 / 700.0, // Ratio de café a agua: 50 g de café a 700 g de agua
                strength = 3
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Slow Pour",
                description = "A recipe with an extremely slow pour for a smooth, strong cup with enhanced sweetness.",
                coffeeType = "Not specified",
                grindLevel = "Medium",
                coffeeToWaterRatio = 30.0 / 500.0, // Ratio de café a agua: 30 g de café a 500 g de agua
                strength = 5
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Slightly Stronger Coffee",
                description = "A recipe for coffee that's slightly stronger than the standard.",
                coffeeType = "Dark Roast",
                grindLevel = "Medium-Coarse",
                coffeeToWaterRatio = 20.0 / 300.0, // Ratio de café a agua: 20 g de café a 300 g de agua (1:15)
                strength = 2
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "V60 Fast Pour",
                description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
                coffeeType = "Medium or dark roast (recommended)",
                grindLevel = "Medium-coarse",
                coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
                strength = 4,
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Chemex Fast Pour",
                description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
                coffeeType = "Medium or dark roast (recommended)",
                grindLevel = "Extra Fine",
                coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
                strength = 4,
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Chemex Strong",
                description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
                coffeeType = "Medium or dark roast (recommended)",
                grindLevel = "Fine",
                coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
                strength = 4,
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            ),
            CoffeeRecipe(
                name = "Chemex calm",
                description = "A strong recipe with a 1:11 coffee to water ratio for a non-bitter cup.",
                coffeeType = "Medium or dark roast (recommended)",
                grindLevel = "Medium-coarse",
                coffeeToWaterRatio = 30.0 / 340.0, // Ratio de café a agua: 30 g de café a 340 g de agua (1:11)
                strength = 4,
                //url_image = "https://cdn-icons-png.flaticon.com/128/8643/8643316.png"
            )
        )
        val data = hashMapOf(
            "email" to email
        )
        try {
            usersCollection.document(UID).set(data).await()

            //Agrego recetas default
            coffeeRecipe.forEach { coffeeRecipe ->
                val recipeDocumentReference = usersCollection.document(UID)
                    .collection("CoffeeRecipes")
                    .add(coffeeRecipe).await()

                // Obtener el ID generado automáticamente y actualizar el objeto CoffeeRecipe
                val generatedId = recipeDocumentReference.id
                // Actualizar el ID también en Firestore
                recipeDocumentReference.update("id", generatedId).await()
            }
        } catch (e: Exception) {
            Log.d(TAG, "User add Fail and Default CoffeeRecipes: ", e)
        }

    }

    suspend fun getAllRecipes(): MutableList<CoffeeRecipe> {

        val coffeeRecipesList = mutableListOf<CoffeeRecipe>()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        try {
            val querySnapshot = uid?.let {
                usersCollection.document(it)
                    .collection("CoffeeRecipes")
                    .get().await()
            }

            if (querySnapshot != null) {
                for (document in querySnapshot.documents) {
                    val coffeeRecipe = document.toObject(CoffeeRecipe::class.java)
                    if (coffeeRecipe != null) {
                        coffeeRecipesList.add(coffeeRecipe)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Cant retrieve CoffeeRecipes: ", e)
        }
        return coffeeRecipesList

    }

    suspend fun getCoffeeRecipeByID(coffeeRecipeID: String): CoffeeRecipe {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        var coffeeRecipe: CoffeeRecipe = CoffeeRecipe()
        var coffeeBean: CoffeeBean = CoffeeBean()

        try {
            val querySnapshotRecipe = uid?.let {
                usersCollection.document(it)
                    .collection("CoffeeRecipes")
                    .document(coffeeRecipeID)
                    .get().await()
            }
            if (querySnapshotRecipe != null) {
                coffeeRecipe = querySnapshotRecipe.toObject<CoffeeRecipe>()!!
            }
        } catch (e: Exception) {
            Log.d(TAG, "Cant retrieve CoffeeRecipes: ", e)
        }
        return coffeeRecipe
    }

    suspend fun addCoffeeRecipe(coffeeRecipe: CoffeeRecipe): Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        try {
            val recipeDocumentReference = uid?.let {
                usersCollection.document(it)
                    .collection("CoffeeRecipes")
                    .add(coffeeRecipe).await()
            }
            // Obtener el ID generado automáticamente y actualizar el objeto CoffeeRecipe
            val generatedId = recipeDocumentReference?.id
            // Actualizar el ID también en Firestore
            recipeDocumentReference?.update("id", generatedId)?.await()
        } catch (e: Exception) {
            Log.d(TAG, "Cant retrieve CoffeeRecipes: ", e)
        }


        return true
    }

    suspend fun updateCoffeeRecipe(coffeeRecipe: CoffeeRecipe):Boolean{
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        try {
            uid?.let {
                // Obtén la referencia del documento CoffeeRecipe
                val recipeDocumentReference = usersCollection.document(it)
                    .collection("CoffeeRecipes")
                    .document(coffeeRecipe.id.toString())

                recipeDocumentReference.update(
                    "name", coffeeRecipe.name,
                    "description", coffeeRecipe.description,
                    "coffeeType", coffeeRecipe.coffeeType,
                    "grindLevel", coffeeRecipe.grindLevel,
                    "coffeeToWaterRatio", coffeeRecipe.coffeeToWaterRatio,
                    "strength", coffeeRecipe.strength,
                    "barcodeCoffeeBean",coffeeRecipe.barcodeCoffeeBean
                ).await()
            }
        } catch (e: Exception) {
            Log.d(TAG, "Failed to update CoffeeRecipe: ", e)
            return false
        }
        return true
    }

    suspend fun getCoffeeBeans() : MutableList<CoffeeBean> {
        val coffeeBeanList = mutableListOf<CoffeeBean>()

        try {
            val querySnapshot = coffeeBeansCollection
                    .get().await()

            if (querySnapshot != null) {
                for (document in querySnapshot.documents) {
                    val coffeeBean = document.toObject(CoffeeBean::class.java)
                    if (coffeeBean != null) {
                        coffeeBeanList.add(coffeeBean)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Cant retrieve CoffeeRecipes: ", e)
        }
        return coffeeBeanList
    }

    suspend fun deleteCoffeeRecipeByID(coffeeRecipeID: String): Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        try {
            val recipeDocumentReference = uid?.let {
                usersCollection.document(it)
                    .collection("CoffeeRecipes")
                    .document(coffeeRecipeID)
                    .delete()
                    .await()
            }
        } catch (e: Exception) {
            Log.d(TAG, "Cant retrieve CoffeeRecipes: ", e)
            return false
        }
        return true
    }
}