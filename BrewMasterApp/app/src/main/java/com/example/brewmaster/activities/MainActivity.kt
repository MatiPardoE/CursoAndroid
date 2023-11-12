package com.example.brewmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.brewmaster.R
import com.example.brewmaster.database.FirestoreDataSource
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        //Prepopulated DB if its empty
        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)
        val fireStoreDB = FirestoreDataSource()
        scope.launch {
            //TODO: La idea es que cada User tenga su coleccion propia.
            var sizeDocs = fireStoreDB.countCoffeeRecipes()
            if(sizeDocs < 1) fireStoreDB.insertCoffeeRecipesPrePopulated()
        }
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        bottomNavView = findViewById(R.id.bottomNavigationBot)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }

}