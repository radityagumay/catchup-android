package com.radityalabs.ui.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radityalabs.ui.TAG
import com.radityalabs.ui.main.di.Scheduler
import com.radityalabs.ui.main.presentation.MainIntent.InputIntent.*
import com.radityalabs.ui.main.presentation.MainIntent.KeyboardIntent.*
import com.radityalabs.ui.main.presentation.MainState.*
import com.radityalabs.ui.main.presentation.model.CountryCodeCurrencies
import com.radityalabs.ui.main.presentation.model.CountryPickerSource.FROM
import com.radityalabs.ui.main.presentation.model.CountryPickerSource.TO
import com.radityalabs.ui.main.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val usecase: MainUseCase,
    private val scheduler: Scheduler
) : ViewModel() {

    private val _states = MutableStateFlow<MainState>(InitialState)
    private val combineKeyedCountryState: StateFlow<CombineKeyedCountryState> =
        _states.transform { item ->
            if (item is CombineKeyedCountryState) {
                emit(item)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            CombineKeyedCountryState("", "", "")
        )
    val states: StateFlow<MainState>
        get() = _states

    init {
        viewModelScope.launch(scheduler.default) {
            launch { usecase.runPeriodicJobToFetchCurrencyExchange() }
            launch { loadSupportCurrencies() }
        }
        combineKeyedCountryState.launchIn(viewModelScope)
    }

    fun processIntents(flows: Flow<MainIntent>) {
        flows.onEach { intent ->
            when (intent) {
                is KeyedIntent -> {
                    _states.value = KeyboardState(intent.number)
                }
                is ConvertIntent -> {
                    processConvertIntent()
                }
                is SelectFromCountryIntent -> {
                    _states.value = OpenCountryPickerState(FROM)
                }
                is SelectToCountryIntent -> {
                    _states.value = OpenCountryPickerState(TO)
                }
                is SelectedCountryCode -> {
                    _states.value = SelectedCountryCodeState(intent.currencies, intent.source.value)
                }
                is CombineKeyedCountryIntent -> {
                    processCombineKeyedIntent(intent)
                }
                else -> {
                    Log.d(TAG, "MainViewModel#processIntents $intent")
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun loadSupportCurrencies() {
        val values = usecase.getSupportCurrencies().map {
            CountryCodeCurrencies(
                it.country,
                it.country + " " + it.value
            )
        }
        _states.value = CountryCurrenciesState(values)
    }

    private fun processCombineKeyedIntent(intent: CombineKeyedCountryIntent) {
        _states.value = CombineKeyedCountryState(
            intent.amount,
            intent.countryFrom,
            intent.countryTo
        )
    }

    private fun processConvertIntent() {
        val (amount, countryFrom, countryTo) = combineKeyedCountryState.value
        if (amount.isEmpty()) {
            _states.value = AmountState
            return
        }
        if (countryFrom.isEmpty()) {
            _states.value = SourceCountryState
            return
        }
        if (countryTo.isEmpty()) {
            _states.value = DestinationCountryState
            return
        }

        viewModelScope.launch(scheduler.default) {
            val value = usecase.convertCurrency(amount, countryFrom, countryTo)
            _states.value = ConversionResultState(value.amount)
        }
    }
}

