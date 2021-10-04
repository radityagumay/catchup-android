package com.radityalabs.network.model

import com.google.gson.annotations.SerializedName

data class ConvertCurrencyResponse(
    @SerializedName("batchList") val batchList: List<Batch>,
    @SerializedName("from") val from: String,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("to") val to: String
) {
    data class Batch(
        @SerializedName("interval") val interval: Int,
        @SerializedName("rates") val rates: List<Double>,
        @SerializedName("startTime") val startTime: Long
    )
}