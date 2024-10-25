package com.khaledamin.prayerapplication.data.model.response

data class Gregorian(
    val date: String,
    val day: String,
    val format: String,
    val month: Month,
    val weekday: Weekday,
    val year: String
)