package com.khaledamin.prayerapplication.data.model.response

data class Hijri(
    val date: String,
    val day: String,
    val designation: Designation,
    val format: String,
    val holidays: List<Any?>,
    val month: MonthX,
    val weekday: WeekdayX,
    val year: String
)