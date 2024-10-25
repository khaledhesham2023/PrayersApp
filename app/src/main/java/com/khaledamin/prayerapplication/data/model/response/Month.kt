package com.khaledamin.prayerapplication.data.model.response

import com.google.gson.annotations.SerializedName

data class Month(
    @SerializedName("en")
    val name: String,
    val number: Int
)