package com.raflisalam.magang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.raflisalam.magang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        setupNavigationMenu()

    }

    private fun setupNavigationMenu() {
        val drawer = binding.drawerLayout
        val navView = binding.navView

        mAppBarConfiguration = AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_karebosi, R.id.nav_daya, R.id.nav_maros, R.id.nav_pangkep)
            .setDrawerLayout(drawer)
            .build()
        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp()
    }

}