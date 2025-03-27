package com.example.myapplication.ui.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showSignUpDialog()
        return View(requireContext()) // No layout needed here
    }


    private fun showSignUpDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_signup, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()



        dialog.show()

        val btnSignUp = dialogView.findViewById<Button>(R.id.btnSignUp)
        val btnLogin = dialogView.findViewById<Button>(R.id.btnLogin)
        val btnSkip = dialogView.findViewById<Button>(R.id.btnSkip)



                btnSignUp.setOnClickListener {
                    dialog.dismiss()

                    findNavController().navigate(R.id.action_signUpFragment_to_signUpDetailsFragment2)
                    dialogView.visibility = View.GONE


                }

                btnLogin.setOnClickListener {
                    // Add login logic or navigate to login screen
                    dialog.dismiss()
                }

                btnSkip.setOnClickListener {
                    // Handle skip (e.g. go to home or main app screen)
                    requireActivity().finish()
                }
            }




}




