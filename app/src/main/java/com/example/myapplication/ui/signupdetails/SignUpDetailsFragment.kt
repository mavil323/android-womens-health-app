package com.example.myapplication.ui.signupdetails

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpDetailsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize FirebaseAuth and Firestore instances
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.sign_up_details, container, false)

        // Set up the sign-up dialog
        showSignUpDetailsDialog(view)

        return view
    }

    private fun showSignUpDetailsDialog(view: View) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.sign_up_details, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false) // Set the dialog to not be dismissible by tapping outside
            .create()

        dialog.show()

        val usernameInput = dialogView.findViewById<EditText>(R.id.usernameInput)
        val emailInput = dialogView.findViewById<EditText>(R.id.emailInput)
        val passwordInput = dialogView.findViewById<EditText>(R.id.passwordInput)
        val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
                checkEmailExists(email, username, password, dialog)
            }


        }
    }

    private fun checkEmailExists(email: String, username: String, password: String, dialog: AlertDialog) {
        // Check if the email is already in use
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // Email is not used, proceed with Firebase Auth sign-up
                    createAccount(email, username, password, dialog)
                } else {
                    Toast.makeText(requireContext(), "Email is already in use.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error checking email", Toast.LENGTH_SHORT).show()
            }
    }

    private fun createAccount(email: String, username: String, password: String, dialog: AlertDialog) {
        // Create a user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // User successfully created, store additional info (username) in Firestore
                    val user = auth.currentUser
                    val userInfo = hashMapOf(
                        "email" to email,
                        "username" to username
                    )

                    firestore.collection("users")
                        .document(user!!.uid) // Use the user ID as the document ID in Firestore
                        .set(userInfo)

                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Sign-up successful", Toast.LENGTH_SHORT).show()
                            activity?.runOnUiThread {
                                dialog.dismiss() // Dismiss the dialog after successful sign-up
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Error saving user info", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(requireContext(), "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

