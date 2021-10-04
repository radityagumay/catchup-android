package com.radityalabs.ui.main.data.impl

import com.radityalabs.network.model.ConvertCurrencyResponse
import com.radityalabs.ui.main.data.MainRepository
import com.radityalabs.ui.main.data.local.LocalDataSource
import com.radityalabs.ui.main.data.local.support.SupportCurrenciesEntity
import com.radityalabs.ui.main.data.model.CurrencyExchange
import com.radityalabs.ui.main.data.model.CurrencyExchange.Companion.toCurrenciesExchangeEntity
import com.radityalabs.ui.main.data.model.SupportCurrencies
import com.radityalabs.ui.main.data.model.SupportCurrencies.Companion.from
import com.radityalabs.ui.main.data.remote.RemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DefaultMainRepository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : MainRepository {
    override suspend fun getSupportCurrencies(): List<SupportCurrencies.Item> {
        val currencies: List<SupportCurrenciesEntity> = local.getCacheSupportCurrencies()
        return if (currencies.isNotEmpty()) {
            from(currencies)
        } else {
            val result = remote.getRemoteSupportCurrencies()
            val item = from(remote.getRemoteSupportCurrencies())
            local.insertSupportedCurrencies(from(item))
            item
        }
    }

    override suspend fun runPeriodicJobToFetchCurrencyExchange() {
        while (true) {
            val value = remote.getRemoteCurrenciesExchange()
            local.insertCurrencyExchange(toCurrenciesExchangeEntity(value))

            delay(Duration.minutes(30))
        }
    }

    override suspend fun getCurrenciesExchange(): List<CurrencyExchange> {
        return CurrencyExchange.toCurrencyExchange(local.getCurrencyExchange())
    }

    override suspend fun convertCurrency(
        countryFrom: String,
        countryTo: String
    ): ConvertCurrencyResponse {
        return remote.convertCurrency(countryFrom, countryTo)
    }
}