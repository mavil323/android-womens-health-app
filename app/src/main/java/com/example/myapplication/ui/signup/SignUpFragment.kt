package com.example.myapplication.ui.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Show Dialog Immediately on Fragment Load
        showSignUpDialog()

        // Return an empty view since no main layout is needed
        return View(requireContext())
    }

    // Pop-up Dialog Logic
    private fun showSignUpDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_signup, null)

        val btnSignUp = dialogView.findViewById<Button>(R.id.btnSignUp)
        val btnLogin = dialogView.findViewById<Button>(R.id.btnLogin)
        val btnSkip = dialogView.findViewById<Button>(R.id.btnSkip)

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)

        val dialog = builder.create()
        dialog.show()

        // Button Click Logic
        btnSignUp.setOnClickListener {
            // Add your Sign Up logic here
            dialog.dismiss()
        }

        btnLogin.setOnClickListener {
            // Add your Log In logic here
            dialog.dismiss()
        }

        btnSkip.setOnClickListener {
            dialog.dismiss()  // Skip closes the dialog
        }
    }
}
