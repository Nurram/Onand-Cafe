package com.tessalonika.onandcafe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var isAdmin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbar.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.menu_contact, R.id.menu_about, R.id.menu_history, R.id.menu_menu), drawerLayout)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        setupActionBarWithNavController(navController, appBarConfiguration)

        val factory = ViewModelFactory(application)
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        viewModel.getUserById()
        viewModel.getIsSuccess().observe(this) {
            isAdmin = it.isAdmin

            if (isAdmin) {
                navView.menu.clear()
                navView.inflateMenu(R.menu.admin_menu)
            }

            navView.setupWithNavController(navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}