package com.radityalabs.network.model

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("privacy") val privacy: String,
    @SerializedName("quotes") val quotes: Map<String, String>,
    @SerializedName("source") val source: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("timestamp") val timestamp: Int,
    @SerializedName("error") val error: Error? = null
) {
    data class Error(
        @SerializedName("code") val code: Int,
        @SerializedName("info") val info: String
    )
}