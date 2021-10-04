package com.radityalabs.ui.main.data.remote.impl

import com.google.gson.Gson
import com.radityalabs.R
import com.radityalabs.helpers.FileReader
import com.radityalabs.network.NetworkService
import com.radityalabs.network.model.ConvertCurrencyResponse
import com.radityalabs.network.model.ExchangeRateResponse
import com.radityalabs.network.model.SupportCurrenciesResponse
import com.radityalabs.ui.main.data.remote.RemoteDataSource
import javax.inject.Inject

class DefaultRemoteDataSource @Inject constructor(
    private val service: NetworkService,
    private val fileReader: FileReader,
    private val gson: Gson
) : RemoteDataSource {
    override suspend fun getRemoteSupportCurrencies(): SupportCurrenciesResponse {
        return runCatching {
            val response = service.getSupportCurrencies()
            return if (response.error != null) {
                getSupportListCurrencyFromFile()
            } else {
                response
            }
        }.getOrDefault(getSupportListCurrencyFromFile())
    }

    override suspend fun getRemoteCurrenciesExchange(): ExchangeRateResponse {
        return runCatching {
            val response = service.getRecentExchangeRate()
            return if (response.error != null) {
                getExchangeRateFromFile()
            } else {
                response
            }
        }.getOrDefault(getExchangeRateFromFile())
    }

    override suspend fun convertCurrency(
        countryFrom: String,
        countryTo: String
    ): ConvertCurrencyResponse {
        return service.getCurrencyExchange(countryFrom, countryTo)
    }

    private fun getSupportListCurrencyFromFile(): SupportCurrenciesResponse {
        return gson.fromJson(
            fileReader.text(R.raw.list_available_currencies),
            SupportCurrenciesResponse::class.java
        )
    }

    private fun getExchangeRateFromFile(): ExchangeRateResponse {
        return gson.fromJson(
            fileReader.text(R.raw.exchange_rate),
            ExchangeRateResponse::class.java
        )
    }
}