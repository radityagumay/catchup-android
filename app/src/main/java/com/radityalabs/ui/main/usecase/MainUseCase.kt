package com.radityalabs.ui.main.usecase

import com.radityalabs.ui.main.data.model.CurrencyExchange
import com.radityalabs.ui.main.data.model.SupportCurrencies
import com.radityalabs.ui.main.usecase.model.ConvertCurrency

interface MainUseCase {
    suspend fun getSupportCurrencies(): List<SupportCurrencies.Item>

    suspend fun getCurrenciesExchange(): List<CurrencyExchange>

    suspend fun runPeriodicJobToFetchCurrencyExchange()

    suspend fun convertCurrency(
        amount: CharSequence,
        countryFrom: CharSequence,
        countryTo: CharSequence
    ): ConvertCurrency
}