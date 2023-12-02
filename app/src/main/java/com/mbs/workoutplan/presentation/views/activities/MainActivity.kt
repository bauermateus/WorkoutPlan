package com.mbs.workoutplan.presentation.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mbs.workoutplan.R
import com.mbs.workoutplan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleBottomNavViewVisibility()
        binding.bottomNavView.setupBottomNavigationWithNavController()
    }

    private fun BottomNavigationView.setupBottomNavigationWithNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(navController)
    }

    private fun handleBottomNavViewVisibility() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    showBottomMenu()
                }

                R.id.profileFragment -> {
                    showBottomMenu()
                }

                else -> {
                    hideBottomMenu()
                }
            }
        }
    }

    private fun showBottomMenu() {
        with(binding) {
            bottomNavView.visibility = View.VISIBLE
        }
    }

    private fun hideBottomMenu() {
        with(binding) {
            bottomNavView.visibility = View.GONE
        }
    }
}