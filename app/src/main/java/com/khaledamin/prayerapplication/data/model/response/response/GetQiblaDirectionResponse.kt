package com.khaledamin.prayerapplication.data.model.response.response

import com.google.gson.annotations.SerializedName

data class GetQiblaDirectionResponse(
    val code: Int,
    @SerializedName("data")
    val qibla: QiblaDTO,
    val status: String
)