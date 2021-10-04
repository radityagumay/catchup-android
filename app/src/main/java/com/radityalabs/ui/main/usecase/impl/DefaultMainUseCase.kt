package com.radityalabs.ui.main.usecase.impl

import com.radityalabs.ui.main.data.model.CurrencyExchange
import com.radityalabs.ui.main.data.MainRepository
import com.radityalabs.ui.main.data.model.SupportCurrencies
import com.radityalabs.ui.main.usecase.MainUseCase
import com.radityalabs.ui.main.usecase.model.ConvertCurrency
import com.radityalabs.ui.widgets.CurrencyFormatter
import javax.inject.Inject

class DefaultMainUseCase @Inject constructor(
    private val repository: MainRepository
) : MainUseCase {
    override suspend fun getSupportCurrencies(): List<SupportCurrencies.Item> {
        return repository.getSupportCurrencies()
    }

    override suspend fun getCurrenciesExchange(): List<CurrencyExchange> {
        return repository.getCurrenciesExchange()
    }

    override suspend fun runPeriodicJobToFetchCurrencyExchange() {
        return repository.runPeriodicJobToFetchCurrencyExchange()
    }

    override suspend fun convertCurrency(
        amount: CharSequence,
        countryFrom: CharSequence,
        countryTo: CharSequence
    ): ConvertCurrency {
        return repository.convertCurrency(
            countryFrom.toString(),
            countryTo.toString()
        ).batchList[0].rates[2].let { rate ->
            val result = amount.toString().replace(",", "").toDouble() * rate
            ConvertCurrency(CurrencyFormatter.priceToString(result))
        }
    }
}
