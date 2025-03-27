package com.example.myapplication.ui.login
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R




    class LogInFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Show the Sign-Up Details dialog
            showLogInDialog()

            // Return a placeholder view (this is required for a Fragment)
            return View(requireContext()) // No layout needed here
        }

        private fun showLogInDialog() {
            val dialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.login_info, null)
            val dialog = AlertDialog.Builder(requireContext())
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

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill in all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "Sign-up details entered!", Toast.LENGTH_SHORT)
                        .show()
                    // Proceed to next step or validation here
                }

                dialog.dismiss() // Dismiss the dialog after submitting
            }
        }
    }
