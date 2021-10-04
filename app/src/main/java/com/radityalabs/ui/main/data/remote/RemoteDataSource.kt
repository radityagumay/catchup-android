package com.radityalabs.ui.main.data.remote

import com.radityalabs.network.model.ConvertCurrencyResponse
import com.radityalabs.network.model.ExchangeRateResponse
import com.radityalabs.network.model.SupportCurrenciesResponse

interface RemoteDataSource {
    suspend fun getRemoteSupportCurrencies(): SupportCurrenciesResponse

    suspend fun getRemoteCurrenciesExchange(): ExchangeRateResponse

    suspend fun convertCurrency(
        countryFrom: String,
        countryTo: String
    ): ConvertCurrencyResponse
}
