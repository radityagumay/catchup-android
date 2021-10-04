package com.radityalabs.ui.main.presentation

import com.radityalabs.ui.main.di.Scheduler
import com.radityalabs.ui.main.presentation.MainIntent.InputIntent.*
import com.radityalabs.ui.main.presentation.MainIntent.KeyboardIntent.*
import com.radityalabs.ui.main.presentation.MainState.*
import com.radityalabs.ui.main.presentation.model.CountryCodeCurrencies
import com.radityalabs.ui.main.presentation.model.CountryPickerSource.FROM
import com.radityalabs.ui.main.presentation.model.CountryPickerSource.TO
import com.radityalabs.ui.main.usecase.MainUseCase
import com.radityalabs.ui.main.usecase.model.ConvertCurrency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var vm: MainViewModel
    private lateinit var usecase: MainUseCase
    private lateinit var scheduler: Scheduler
    private val testDispatcher = coroutineRule.testDispatcher

    @Before
    fun setup() {
        usecase = mock()
        scheduler = object : Scheduler {
            override val io: CoroutineDispatcher
                get() = testDispatcher
            override val default: CoroutineDispatcher
                get() = testDispatcher
            override val main: CoroutineDispatcher
                get() = testDispatcher
        }

        vm = MainViewModel(usecase, scheduler)
    }

    @Test
    fun KeyedIntent_isProceed_Expect_Invoke_KeyboardState() {
        runBlocking {
            vm.processIntents(flowOf(KeyedIntent("1")))
            val expect = KeyboardState("1")
            assertEquals(expect, vm.states.value)
        }
    }

    @Test
    fun SelectFromCountryIntent_isProceed_Expect_Invoke_OpenCountryPickerState() {
        runBlocking {
            vm.processIntents(flowOf(SelectFromCountryIntent))
            val expect = OpenCountryPickerState(FROM)
            assertEquals(expect, vm.states.value)
        }
    }

    @Test
    fun SelectToCountryIntent_isProceed_Expect_Invoke_OpenCountryPickerState() {
        runBlocking {
            vm.processIntents(flowOf(SelectToCountryIntent))
            val expect = OpenCountryPickerState(TO)
            assertEquals(expect, vm.states.value)
        }
    }

    @Test
    fun SelectedCountryCode_isProceed_Expect_Invoke_SelectedCountryCodeState() {
        runBlocking {
            val country = CountryCodeCurrencies("USD", "United State Dollar")
            vm.processIntents(flowOf(SelectedCountryCode(country, FROM)))
            val expect = SelectedCountryCodeState(country, FROM.value)
            assertEquals(expect, vm.states.value)
        }
    }

    @Test
    fun CombineKeyedCountryIntent_isProceed_Expect_Invoke_CombineKeyedCountryState() {
        runBlocking {
            vm.processIntents(flowOf(CombineKeyedCountryIntent("1", "USD", "IDR")))
            val expect = CombineKeyedCountryState("1", "USD", "IDR")
            assertEquals(expect, vm.states.value)
        }
    }

    @Test
    fun ConvertIntent_isProceed_Expect_Invoke_ConversionResultState() {
        runBlocking {
            whenever(
                usecase.convertCurrency("1", "USD", "IDR")
            ).thenReturn(ConvertCurrency("14000"))

            vm.processIntents(
                flowOf(
                    CombineKeyedCountryIntent("1", "USD", "IDR"),
                    ConvertIntent
                )
            )
            val expect = ConversionResultState("14000")
            assertEquals(expect, vm.states.value)
        }
    }
}