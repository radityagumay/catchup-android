package com.radityalabs.network

import com.radityalabs.network.model.ConvertCurrencyResponse
import com.radityalabs.network.model.ExchangeRateResponse
import com.radityalabs.network.model.SupportCurrenciesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService {
    @GET("live?access_key=6d1c0803c00580899eaca88876ba5347")
    suspend fun getRecentExchangeRate(): ExchangeRateResponse

    @GET("list?access_key=6d1c0803c00580899eaca88876ba5347")
    suspend fun getSupportCurrencies(): SupportCurrenciesResponse

    @Headers("Authorization: Bearer bG9kZXN0YXI6N0pZOGozRjJOTk1mamYwY1VsWkh3YmdDelhoZGtRZUs")
    @GET("https://www.xe.com/api/protected/charting-rates/")
    suspend fun getCurrencyExchange(
        @Query("fromCurrency") fromCountry: String,
        @Query("toCurrency") toCountry: String,
    ): ConvertCurrencyResponse
}