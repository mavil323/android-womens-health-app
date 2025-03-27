package com.example.myapplication.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity

class LogInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Show the Log In dialog
        showLogInDialog()

        // Return a placeholder view (this is required for a Fragment)
        return View(requireContext()) // No layout needed here
    }

    private fun showLogInDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.login_info, null)
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false) // You can change this if you want the dialog to be dismissible
            .create()

        dialog.show()

        val emailInput = dialogView.findViewById<EditText>(R.id.emailInput)
        val passwordInput = dialogView.findViewById<EditText>(R.id.passwordInput)
        val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Proceed to log the user in
                logInUser(email, password, dialog)
            }
        }
    }

    private fun logInUser(email: String, password: String, dialog: android.app.AlertDialog) {
        // Firebase login process
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Login successful, dismiss the dialog
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    (activity as? AppCompatActivity)?.supportActionBar?.apply {
                        setDisplayHomeAsUpEnabled(true) // Optionally add a back button
                        setHomeAsUpIndicator(R.drawable.ic_action_name) // Add your icon here
                    }
                    // You can navigate to the next screen or fragment here after successful login
                } else {
                    // Login failed
                    Toast.makeText(requireContext(), "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
