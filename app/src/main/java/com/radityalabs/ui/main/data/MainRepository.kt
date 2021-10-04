package com.radityalabs.ui.main.data

import com.radityalabs.network.model.ConvertCurrencyResponse
import com.radityalabs.ui.main.data.model.CurrencyExchange
import com.radityalabs.ui.main.data.model.SupportCurrencies

interface MainRepository {
    suspend fun getSupportCurrencies(): List<SupportCurrencies.Item>

    suspend fun runPeriodicJobToFetchCurrencyExchange()

    suspend fun getCurrenciesExchange(): List<CurrencyExchange>

    suspend fun convertCurrency(
        countryFrom: String,
        countryTo: String
    ): ConvertCurrencyResponse
}