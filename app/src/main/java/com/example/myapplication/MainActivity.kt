package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init DrawerLayout (from root layout)
        drawerLayout = findViewById(R.id.drawer_layout)

        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isFirstTime = sharedPref.getBoolean("isFirstTime", true)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )

        //new
        fun openDrawer() {
            val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
            drawerLayout.openDrawer(androidx.core.view.GravityCompat.START)
        }
        navView.setupWithNavController(navController)

        // Only show sign-up dialog the first time
        if (isFirstTime) {
            showSignUpDialog()
            sharedPref.edit().putBoolean("isFirstTime", false).apply()
        }

        // Set up the drawer menu click listener
        val drawerNavView: NavigationView = findViewById(R.id.nav_view_drawer)
        drawerNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()
                    // TODO: Add real logout logic here
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    private fun showSignUpDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_signup, null)

        val btnSignUp = dialogView.findViewById<Button>(R.id.btnSignUp)
        val btnLogin = dialogView.findViewById<Button>(R.id.btnLogin)
        val btnSkip = dialogView.findViewById<Button>(R.id.btnSkip)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = builder.create()

        // Example setup (you can add your logic here)
        btnSignUp.setOnClickListener {
            Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnLogin.setOnClickListener {
            Toast.makeText(this, "Login clicked", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnSkip.setOnClickListener {
            dialog.dismiss()
        }


    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }
}
