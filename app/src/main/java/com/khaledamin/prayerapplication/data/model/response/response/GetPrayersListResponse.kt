package com.khaledamin.prayerapplication.data.model.response.response

import com.google.gson.annotations.SerializedName
import com.khaledamin.prayerapplication.data.model.response.DayDTO

data class GetPrayersListResponse(
    val code: Int,
    @SerializedName("data")
    val daysDTO: List<DayDTO>,
    val status: String
)