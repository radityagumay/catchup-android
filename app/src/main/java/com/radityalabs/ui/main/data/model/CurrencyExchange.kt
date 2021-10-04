package com.radityalabs.ui.main.data.model

import com.radityalabs.network.model.ExchangeRateResponse
import com.radityalabs.ui.main.data.local.exchange.CurrenciesExchangeEntity

data class CurrencyExchange(
    val fromCountry: String, val toCountry: String, val rates: Double
) {
    companion object {
        fun toCurrenciesExchangeEntity(source: ExchangeRateResponse): List<CurrenciesExchangeEntity> {
            return source.quotes.map {
                val countryFrom = it.key.take(3)
                val countryTo = it.key.takeLast(it.key.length - 3)
                CurrenciesExchangeEntity(
                    fromCountry = countryFrom,
                    toCountry = countryTo,
                    rates = it.value.toDouble()
                )
            }
        }

        fun toCurrencyExchange(source: List<CurrenciesExchangeEntity>): List<CurrencyExchange> {
            return source.map {
                CurrencyExchange(it.fromCountry, it.toCountry, it.rates)
            }
        }
    }
}