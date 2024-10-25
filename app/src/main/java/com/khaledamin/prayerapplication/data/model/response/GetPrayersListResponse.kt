package com.khaledamin.prayerapplication.data.model.response

import com.google.gson.annotations.SerializedName

data class GetPrayersListResponse(
    val code: Int,
    @SerializedName("data")
    val daysDTO: List<DayDTO>,
    val status: String
)