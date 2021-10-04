package com.radityalabs.ui.main.data.local

import com.radityalabs.ui.main.data.local.exchange.CurrenciesExchangeEntity
import com.radityalabs.ui.main.data.local.support.SupportCurrenciesEntity

interface LocalDataSource {
    suspend fun getCacheSupportCurrencies(): List<SupportCurrenciesEntity>

    suspend fun insertSupportedCurrencies(items: List<SupportCurrenciesEntity>)

    suspend fun insertCurrencyExchange(items: List<CurrenciesExchangeEntity>)

    suspend fun getCurrencyExchange(): List<CurrenciesExchangeEntity>
}
