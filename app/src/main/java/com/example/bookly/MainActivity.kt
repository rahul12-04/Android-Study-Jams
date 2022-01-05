package com.example.bookly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController

    //this companion object is for the purpose of development only
    companion object{
        val dateAdded = "5-02-2022"
        val libdb = listOf<Book>(
            Book( "Three men in a boat",1, dateAdded),
            Book("Mechanics-Timoshenko",4,dateAdded),
            Book("Cengage JEE maths",8,dateAdded),
            Book("The invisible man",10,dateAdded),
            Book( "Inorganic chemistry-J D Lee",903,dateAdded),
            Book( "H C Verma",1003,dateAdded),
            Book( "Half girlfriend",1353,dateAdded),
            Book( "Fundamentals of integral calculus",43563,dateAdded),
            Book( "Wings of fire",1482454,dateAdded),
            Book( "Around the world in 80 days",2039202456,dateAdded),
            Book( "Let us C",1040,dateAdded),
            Book( "Let us C++",2053,dateAdded),
            Book( "An excursion in mathematics",888888,dateAdded),
            Book( "Basics of Machine Learning",14,dateAdded),
            Book( "Object Oriented Systems",20392,dateAdded)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}