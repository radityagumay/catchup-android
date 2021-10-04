package com.radityalabs.ui.main.presentation

import com.radityalabs.ui.main.presentation.model.CountryCodeCurrencies
import com.radityalabs.ui.main.presentation.model.CountryPickerSource

sealed class MainIntent {
    sealed class KeyboardIntent : MainIntent() {
        object Initial : MainIntent()
        object DeleteIntent : MainIntent()
        object ConvertIntent : MainIntent()
        data class KeyedIntent(val number: String) : KeyboardIntent()
        data class CombineKeyedCountryIntent(
            val amount: CharSequence,
            val countryFrom: CharSequence,
            val countryTo: CharSequence
        ) : KeyboardIntent()
    }

    sealed class InputIntent : MainIntent() {
        object SelectFromCountryIntent : MainIntent()
        object SelectToCountryIntent : MainIntent()
        data class SelectedCountryCode(
            val currencies: CountryCodeCurrencies,
            val source: CountryPickerSource
        ) : MainIntent()
    }
}