package com.radityalabs.ui.main.presentation

import com.radityalabs.ui.main.presentation.model.CountryCodeCurrencies
import com.radityalabs.ui.main.presentation.model.CountryPickerSource

sealed class MainState {
    object InitialState : MainState()
    object AmountState: MainState()
    object SourceCountryState: MainState()
    object DestinationCountryState: MainState()

    data class OpenCountryPickerState(val source: CountryPickerSource) : MainState()
    data class CountryCurrenciesState(val list: List<CountryCodeCurrencies>) : MainState()
    data class KeyboardState(val number: String) : MainState()
    data class ConversionResultState(val amount: String) : MainState()
    data class SelectedCountryCodeState(
        val currencies: CountryCodeCurrencies,
        val source: String
    ) : MainState()
    data class CombineKeyedCountryState(
        val amount: CharSequence,
        val countryFrom: CharSequence,
        val countryTo: CharSequence
    ) : MainState()
}