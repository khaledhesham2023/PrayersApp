package com.khaledamin.prayerapplication.domain.model

data class Day(
    val timings: ArrayList<Timing>,
    val readable: String,
    val date: String,
    val latitude:Double,
    val longitude:Double
)