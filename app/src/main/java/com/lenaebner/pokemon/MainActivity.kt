package com.lenaebner.pokemon


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.lenaebner.pokemon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val RECORD_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appBarConfig = AppBarConfiguration(topLevelDestinationIds = setOf(
              R.id.homeFragment
                //R.id.loginFragment
        ))


        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Home"

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.toolbar.setupWithNavController(navController, appBarConfig)
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.itemIconTintList = null
        //binding.bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }

}