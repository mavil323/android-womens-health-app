package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navDrawer: NavigationView
    fun updateDrawerForUserState(isLoggedIn: Boolean) {
        val navDrawer = findViewById<NavigationView>(R.id.nav_view_drawer)

        navDrawer.menu.clear()

        if (isLoggedIn) {
            navDrawer.inflateMenu(R.menu.drawer_menu) // your logged-in menu
        } else {
            navDrawer.inflateMenu(R.menu.nav_sign_in) // your logged-out menu
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize drawer layout and navigation views
        drawerLayout = binding.root.findViewById(R.id.drawer_layout)
        navDrawer = binding.root.findViewById(R.id.nav_view_drawer)
       // loggedInDrawer = binding.root.findViewById(R.id.nav_view_logged_in)
       // loggedOutDrawer = binding.root.findViewById(R.id.nav_view_logged_out)

        // Setup bottom navigation + nav controller
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        )

        navView.setupWithNavController(navController)

        // First-time sign-up dialog
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("isFirstTime", true)) {
            showSignUpDialog()
            sharedPref.edit().putBoolean("isFirstTime", false).apply()
        }
        navDrawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                    updateDrawerForUserState(false)
                    true
                }
                R.id.nav_sign_in -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, com.example.myapplication.ui.login.LogInFragment())
                        .commit()
                    true
                }
                else -> false
            }.also {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }


        // Handle menu actions for both drawers
//        loggedInDrawer.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.nav_logout -> {
//                    FirebaseAuth.getInstance().signOut()
//                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
//                    updateDrawerForUserState(false)
//
//                    drawerLayout.openDrawer(GravityCompat.END)
//
//                    true
//                }
//                else -> false
//            }
//        }

//        loggedOutDrawer.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.nav_sign_in -> {
//                    Toast.makeText(this, "Opening login...", Toast.LENGTH_SHORT).show()
//                    drawerLayout.closeDrawer(GravityCompat.START)
//
//
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.nav_host_fragment_activity_main, com.example.myapplication.ui.login.LogInFragment())
//                        .commit()
//                    true
//                }
//                else -> false
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        updateDrawerForUserState(FirebaseAuth.getInstance().currentUser != null)
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

//    fun updateDrawerForUserState(isLoggedIn: Boolean) {
//        if (isLoggedIn) {
//            findViewById<View>(R.id.nav_view_logged_in).visibility = View.VISIBLE
//            findViewById<View>(R.id.nav_view_logged_out).visibility = View.GONE
//        } else {
//            findViewById<View>(R.id.nav_view_logged_in).visibility = View.GONE
//            findViewById<View>(R.id.nav_view_logged_out).visibility = View.VISIBLE
//        }
//    }

    private fun showSignUpDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_signup, null)
        val btnSignUp = dialogView.findViewById<Button>(R.id.btnSignUp)
        val btnLogin = dialogView.findViewById<Button>(R.id.btnLogin)
        val btnSkip = dialogView.findViewById<Button>(R.id.btnSkip)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

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
}
