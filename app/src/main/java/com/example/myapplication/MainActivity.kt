package com.example.myapplication

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isFirstTime = sharedPref.getBoolean("isFirstTime", true)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )

        )
            if (isFirstTime) {
                // Step 2: Show the pop-up dialog
                showSignUpDialog()

                // Step 3: Mark that the pop-up has been shown
                sharedPref.edit().putBoolean("isFirstTime", false).apply()
            }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    private fun showSignUpDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_signup, null)

        val btnSignUp = dialogView.findViewById<Button>(R.id.btnSignUp)
        val btnLogin = dialogView.findViewById<Button>(R.id.btnLogin)
        val btnSkip = dialogView.findViewById<Button>(R.id.btnSkip)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)  // Prevents users from bypassing without selecting an option

        val dialog = builder.create()
        dialog.show()

        // Step 4: Handle Button Actions
      /*  btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            dialog.dismiss()
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            dialog.dismiss()
        }
*/
        btnSkip.setOnClickListener {
            dialog.dismiss() // Close the pop-up without navigation
        }
    }
}