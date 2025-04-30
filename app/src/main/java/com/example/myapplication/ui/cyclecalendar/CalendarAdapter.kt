package com.example.myapplication.ui.cyclecalendar

import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class CalendarAdapter(
    private val days: MutableList<DayData>,
    private val onDayDoubleTap: (DayData) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {

    inner class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayNumber: TextView = view.findViewById(R.id.day_number)
        val dayContainer: FrameLayout = view.findViewById(R.id.day_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        holder.dayNumber.text = if (day.day == 0) "" else day.day.toString()

        when {
            day.note != null -> holder.dayContainer.setBackgroundResource(R.drawable.bg_note_day)
          //  day.isBirthday -> holder.dayContainer.setBackgroundResource(R.drawable.bg_birthday_day)
            day.isPeriod -> holder.dayContainer.setBackgroundResource(R.drawable.bg_day_period)
            day.isFertile -> holder.dayContainer.setBackgroundResource(R.drawable.bg_day_ovulation)
            else -> holder.dayContainer.setBackgroundResource(R.drawable.bg_day_default)
        }

        val gestureDetector = GestureDetector(holder.itemView.context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    if (day.day != 0) onDayDoubleTap(day)
                    return true
                }
            })

        holder.itemView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    override fun getItemCount(): Int = days.size

    fun updateData(newDays: List<DayData>) {
        days.clear()
        days.addAll(newDays)
        notifyDataSetChanged()
    }
}
