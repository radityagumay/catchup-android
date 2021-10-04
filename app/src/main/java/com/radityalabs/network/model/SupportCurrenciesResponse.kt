package com.radityalabs.network.model

import com.google.gson.annotations.SerializedName

data class SupportCurrenciesResponse(
    @SerializedName("currencies") val currencies: Map<String, String>,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("error") val error: Error? = null
) {
    data class Error(
        @SerializedName("code") val code: Int,
        @SerializedName("info") val info: String
    )
}