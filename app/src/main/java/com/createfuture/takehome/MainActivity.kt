package com.createfuture.takehome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.createfuture.takehome.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Main entry point of the application.
 *
 * This activity hosts the navigation component and sets up the bottom navigation bar
 * to allow users to switch between different fragments defined in the navigation graph.
 */
class MainActivity : AppCompatActivity() {

    // View binding for the activity's layout
    private lateinit var binding: ActivityMainBinding

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * Sets up the content view, navigation controller, and binds the bottom navigation view.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up bottom navigation with NavController
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}
