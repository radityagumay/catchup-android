package com.radityalabs.ui.main.data.remote.impl

import com.google.gson.Gson
import com.radityalabs.R
import com.radityalabs.helpers.FileReader
import com.radityalabs.network.NetworkService
import com.radityalabs.network.model.ConvertCurrencyResponse
import com.radityalabs.network.model.ExchangeRateResponse
import com.radityalabs.network.model.SupportCurrenciesResponse
import com.radityalabs.ui.main.data.remote.RemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DefaultRemoteDataSourceTest {

    private lateinit var sut: RemoteDataSource
    private lateinit var service: NetworkService
    private lateinit var fileReader: FileReader
    private lateinit var gson: Gson

    @Before
    fun setup() {
        service = mock()
        fileReader = mock()
        gson = Gson()
        sut = DefaultRemoteDataSource(service, fileReader, gson)
    }

    @Test
    fun getRemoteSupportCurrencies_ApiIsSuccess_Expect_ReturnedResponse(): Unit = runBlocking {
        val expected = gson.fromJson(CURRENCIES_SUCCESS, SupportCurrenciesResponse::class.java)
        whenever(
            service.getSupportCurrencies()
        ).thenReturn(expected)

        val actual = sut.getRemoteSupportCurrencies()
        assertEquals(expected, actual)
    }

    @Test
    fun getRemoteSupportCurrencies_ApiIsReturnErrorCode106_Expect_ReturnedResponse_FromFile(): Unit =
        runBlocking {
            val errorApi =
                gson.fromJson(CURRENCIES_ERROR_106, SupportCurrenciesResponse::class.java)
            val expected =
                gson.fromJson(CURRENCIES_SUCCESS, SupportCurrenciesResponse::class.java)
            whenever(
                service.getSupportCurrencies()
            ).thenReturn(errorApi)
            whenever(
                fileReader.text(R.raw.list_available_currencies)
            ).thenReturn(gson.toJson(expected))

            val actual = sut.getRemoteSupportCurrencies()
            assertEquals(expected, actual)
        }

    @Test
    fun getRemoteSupportCurrencies_ApiIsThrowError_Expect_ReturnedResponse_FromFile(): Unit =
        runBlocking {
            val expected =
                gson.fromJson(CURRENCIES_SUCCESS, SupportCurrenciesResponse::class.java)
            whenever(
                service.getSupportCurrencies()
            ).thenThrow(IllegalArgumentException("broken!"))

            whenever(
                fileReader.text(R.raw.list_available_currencies)
            ).thenReturn(gson.toJson(expected))

            val actual = sut.getRemoteSupportCurrencies()
            assertEquals(expected, actual)
        }

    @Test
    fun getRemoteCurrenciesExchange_ApiIsSuccess_Expect_ReturnedResponse(): Unit = runBlocking {
        val expected = gson.fromJson(EXCHANGE_SUCCESS, ExchangeRateResponse::class.java)
        whenever(
            service.getRecentExchangeRate()
        ).thenReturn(expected)

        val actual = sut.getRemoteCurrenciesExchange()
        assertEquals(expected, actual)
    }

    @Test
    fun getRemoteCurrenciesExchange_ApiIsReturnErrorCode106_Expect_ReturnedResponse_FromFile(): Unit =
        runBlocking {
            val errorApi =
                gson.fromJson(EXCHANGE_ERROR_106, ExchangeRateResponse::class.java)
            val expected =
                gson.fromJson(EXCHANGE_SUCCESS, ExchangeRateResponse::class.java)
            whenever(
                service.getRecentExchangeRate()
            ).thenReturn(errorApi)
            whenever(
                fileReader.text(R.raw.exchange_rate)
            ).thenReturn(gson.toJson(expected))

            val actual = sut.getRemoteCurrenciesExchange()
            assertEquals(expected, actual)
        }

    @Test
    fun getRemoteCurrenciesExchange_ApiIsThrowError_Expect_ReturnedResponse_FromFile(): Unit =
        runBlocking {
            val expected =
                gson.fromJson(EXCHANGE_SUCCESS, ExchangeRateResponse::class.java)
            whenever(
                service.getRecentExchangeRate()
            ).thenThrow(IllegalArgumentException("broken!"))

            whenever(
                fileReader.text(R.raw.exchange_rate)
            ).thenReturn(gson.toJson(expected))

            val actual = sut.getRemoteCurrenciesExchange()
            assertEquals(expected, actual)
        }

    @Test
    fun convertCurrency_ApiIsSuccess_Expect_ReturnedResponse(): Unit =
        runBlocking {
            val expected =
                gson.fromJson(CURRENCY_CONVERTER_SUCCESS, ConvertCurrencyResponse::class.java)
            whenever(
                service.getCurrencyExchange("USD", "IDR")
            ).thenReturn(expected)

            val actual = sut.convertCurrency("USD", "IDR")
            assertEquals(expected, actual)
        }
}