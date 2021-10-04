package com.radityalabs.ui.main.data.local.impl

import com.radityalabs.ui.main.data.local.LocalDataSource
import com.radityalabs.ui.main.data.local.exchange.CurrenciesExchangeDao
import com.radityalabs.ui.main.data.local.exchange.CurrenciesExchangeEntity
import com.radityalabs.ui.main.data.local.support.SupportCurrenciesDao
import com.radityalabs.ui.main.data.local.support.SupportCurrenciesEntity
import javax.inject.Inject

class DefaultLocalDataSource @Inject constructor(
    private val currenciesDao: SupportCurrenciesDao,
    private val exchangeDao: CurrenciesExchangeDao
) : LocalDataSource {
    override suspend fun getCacheSupportCurrencies(): List<SupportCurrenciesEntity> {
        return currenciesDao.getSupportCurrencies()
    }

    override suspend fun insertSupportedCurrencies(items: List<SupportCurrenciesEntity>) {
        currenciesDao.insert(items)
    }

    override suspend fun insertCurrencyExchange(items: List<CurrenciesExchangeEntity>) {
        exchangeDao.insert(items)
    }

    override suspend fun getCurrencyExchange(): List<CurrenciesExchangeEntity> {
        return exchangeDao.getCurrenciesExchange()
    }
}
