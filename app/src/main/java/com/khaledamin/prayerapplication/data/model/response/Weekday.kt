package com.khaledamin.prayerapplication.data.model.response

import com.google.gson.annotations.SerializedName

data class Weekday(
    @SerializedName("en")
    val dayName: String
)