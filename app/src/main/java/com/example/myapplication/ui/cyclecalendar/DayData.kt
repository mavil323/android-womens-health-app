package com.example.myapplication.ui.cyclecalendar

data class DayData(
    val day: Int,
    val isPeriod: Boolean = false,
    val isFertile: Boolean = false,
    val isBirthday: Boolean = false,
    var note: String? = null
)
