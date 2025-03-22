package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.CalendarView
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.Builder
import androidx.lifecycle.ViewModelProvider


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val calendarView: CalendarView = binding.calendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            Toast.makeText(requireContext(), "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()
        }
        // Initialize DatePicker Button
        val btnDatePicker: Button = binding.btnDatePicker
        btnDatePicker.setOnClickListener {
            val datePicker = Builder.datePicker()
                .setTitleText("Select a Date")
                .build()

            // Show the DatePicker
            datePicker.show(parentFragmentManager, "DATE_PICKER")

            // Handle Date Selection
            datePicker.addOnPositiveButtonClickListener { selection ->
                Toast.makeText(
                    requireContext(),
                    "Selected Date: ${datePicker.headerText}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        //val textView: TextView = binding.textHome
        //homeViewModel.text.observe(viewLifecycleOwner) {
          //  textView.text = it
       // }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}