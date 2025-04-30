package com.example.myapplication.ui.cyclecalendar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var textMonthYear: TextView
    private lateinit var btnPrevMonth: Button
    private lateinit var btnNextMonth: Button
    private lateinit var calendarAdapter: CalendarAdapter

    private val currentCalendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    private val prefs by lazy {
        requireContext().getSharedPreferences("calendar_notes", android.content.Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        textMonthYear = view.findViewById(R.id.textMonthYear)
        btnPrevMonth = view.findViewById(R.id.btnPrevMonth)
        btnNextMonth = view.findViewById(R.id.btnNextMonth)

        calendarRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        calendarAdapter = CalendarAdapter(mutableListOf()) { day -> showNoteDialog(day) }
        calendarRecyclerView.adapter = calendarAdapter

        btnPrevMonth.setOnClickListener {
            currentCalendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        btnNextMonth.setOnClickListener {
            currentCalendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        updateCalendar()
        return view
    }

    private fun updateCalendar() {
        val days = generateMonthCalendar(currentCalendar)
        textMonthYear.text = dateFormat.format(currentCalendar.time)
        calendarAdapter.updateData(days)
    }

    private fun generateMonthCalendar(reference: Calendar): List<DayData> {
        val days = mutableListOf<DayData>()
        val tempCalendar = reference.clone() as Calendar
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1)

        val firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1
        val maxDay = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 0 until firstDayOfWeek) {
            days.add(DayData(0))
        }

        val cycleLength = 28
        val periodLength = 5
        val fertileWindowStartOffset = 9

        val periodStart = Calendar.getInstance()
        periodStart.set(2025, Calendar.APRIL, 4)

        for (day in 1..maxDay) {
            tempCalendar.set(Calendar.DAY_OF_MONTH, day)

            val isPeriod = isPredictedPeriod(tempCalendar.time, periodStart.time, cycleLength, periodLength)
            val isFertile = isPredictedFertile(tempCalendar.time, periodStart.time, cycleLength, fertileWindowStartOffset)
            val isBirthday = day == 18

            val dayData = DayData(day, isPeriod, isFertile, isBirthday)
            loadNoteForDate(dayData, reference.get(Calendar.YEAR), reference.get(Calendar.MONTH))
            days.add(dayData)
        }

        return days
    }

    private fun isPredictedPeriod(current: Date, start: Date, cycleLength: Int, periodLength: Int): Boolean {
        val diff = (current.time - start.time) / (1000 * 60 * 60 * 24)
        if (diff < 0) return false
        val daysSinceLastPeriod = diff % cycleLength
        return daysSinceLastPeriod < periodLength
    }

    private fun isPredictedFertile(current: Date, start: Date, cycleLength: Int, fertileOffset: Int): Boolean {
        val diff = (current.time - start.time) / (1000 * 60 * 60 * 24)
        if (diff < 0) return false
        val daysSinceLastPeriod = diff % cycleLength
        return daysSinceLastPeriod in fertileOffset..(fertileOffset + 5)
    }

    private fun saveNoteForDate(day: DayData, year: Int, month: Int) {
        val key = "$year-${month + 1}-${day.day}"
        prefs.edit().putString(key, day.note).apply()
    }

    private fun loadNoteForDate(day: DayData, year: Int, month: Int) {
        val key = "$year-${month + 1}-${day.day}"
        day.note = prefs.getString(key, null)
    }

    private fun showNoteDialog(day: DayData) {
        val input = EditText(requireContext())
        input.setText(day.note ?: "")
        input.hint = "Enter a note"

        AlertDialog.Builder(requireContext())
            .setTitle("Note for ${day.day} ${dateFormat.format(currentCalendar.time)}")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val noteText = input.text.toString()
                day.note = noteText
                saveNoteForDate(day, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH))
                calendarAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
