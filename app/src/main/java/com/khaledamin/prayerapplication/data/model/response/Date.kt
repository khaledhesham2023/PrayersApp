package com.khaledamin.prayerapplication.data.model.response

data class Date(
    val gregorian: Gregorian,
    val readable: String,
    val timestamp: String
)