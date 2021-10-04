package com.radityalabs.ui.main.presentation

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView.BufferType.EDITABLE
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.radityalabs.R
import com.radityalabs.R.string.amount_unfilled
import com.radityalabs.R.string.destination_country_not_selected
import com.radityalabs.R.string.source_country_not_selected
import com.radityalabs.databinding.ActivityMainBinding
import com.radityalabs.databinding.InputLayoutBinding
import com.radityalabs.ui.TAG
import com.radityalabs.ui.main.presentation.MainIntent.InputIntent.SelectFromCountryIntent
import com.radityalabs.ui.main.presentation.MainIntent.InputIntent.SelectToCountryIntent
import com.radityalabs.ui.main.presentation.MainIntent.InputIntent.SelectedCountryCode
import com.radityalabs.ui.main.presentation.MainIntent.KeyboardIntent.CombineKeyedCountryIntent
import com.radityalabs.ui.main.presentation.MainState.AmountState
import com.radityalabs.ui.main.presentation.MainState.ConversionResultState
import com.radityalabs.ui.main.presentation.MainState.CountryCurrenciesState
import com.radityalabs.ui.main.presentation.MainState.DestinationCountryState
import com.radityalabs.ui.main.presentation.MainState.KeyboardState
import com.radityalabs.ui.main.presentation.MainState.OpenCountryPickerState
import com.radityalabs.ui.main.presentation.MainState.SelectedCountryCodeState
import com.radityalabs.ui.main.presentation.MainState.SourceCountryState
import com.radityalabs.ui.main.presentation.model.CountryCodeCurrencies
import com.radityalabs.ui.main.presentation.model.CountryPickerSource
import com.radityalabs.ui.widgets.CountryDialogPicker
import com.radityalabs.ui.widgets.throttleFirst
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.textChanges

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm: MainViewModel by viewModels()
    private val countryDialogPicker by lazy { CountryDialogPicker(this) }

    private lateinit var binding: ActivityMainBinding
    private lateinit var input: InputLayoutBinding

    private var currentSourceSelection = CountryPickerSource.FROM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val flows = setupViewFlows()
        vm.states.onEach(::states).launchIn(lifecycleScope)
        vm.processIntents(flows)
    }

    private fun setupViewFlows(): Flow<MainIntent> {
        return merge(
            setupKeyboardFlow(),
            setupInputFlow(),
            setupDialogPickerFlow()
        )
    }

    private fun setupKeyboardFlow(): Flow<MainIntent> {
        return binding.customKeyboardLayout.keyboardFlow()
    }

    private fun setupInputFlow(): Flow<MainIntent> {
        input = InputLayoutBinding.bind(binding.root)
        input.etFrom.showSoftInputOnFocus = false
        input.etFrom.requestFocus()

        val ivCountryFrom = input.ivCountryFrom
            .clicks().throttleFirst(1000).map { SelectFromCountryIntent }
        val ivCountryTo = input.ivCountryTo
            .clicks().throttleFirst(1000).map { SelectToCountryIntent }

        return merge(
            ivCountryFrom, ivCountryTo,
            combine(
                listOf(
                    input.etFrom.textChanges(),
                    input.tvCountryCodeFrom.textChanges(),
                    input.tvCountryCodeTo.textChanges()
                )
            ) { arr ->
                val etFrom = arr[0]
                val countryFrom = arr[1]
                val countryTo = arr[2]
                CombineKeyedCountryIntent(etFrom, countryFrom, countryTo)
            }
        )
    }

    private fun setupDialogPickerFlow(): Flow<MainIntent> {
        return countryDialogPicker.countrySelectedFlow().map {
            SelectedCountryCode(it, currentSourceSelection)
        }
    }

    private fun states(state: MainState?) {
        when (state) {
            is CountryCurrenciesState -> countryDialogPicker.submitList(state.list)
            is KeyboardState -> keyboardIntent(state)
            is ConversionResultState -> input.etTo.text = state.amount
            is OpenCountryPickerState -> {
                currentSourceSelection = state.source
                countryDialogPicker.show()
            }
            is SelectedCountryCodeState -> {
                if (CountryPickerSource.FROM.value == state.source) {
                    input.tvCountryCodeFrom.text = state.currencies.countryCode
                } else {
                    input.tvCountryCodeTo.text = state.currencies.countryCode
                }
                setSelectedCountry(state.currencies, state.source)
            }
            is AmountState -> {
                Toast.makeText(this, getString(amount_unfilled), LENGTH_SHORT).show()
            }
            is DestinationCountryState -> {
                Toast.makeText(this, getString(destination_country_not_selected), LENGTH_SHORT)
                    .show()
            }
            is SourceCountryState -> {
                Toast.makeText(this, getString(source_country_not_selected), LENGTH_SHORT).show()
            }
            else -> {
                Log.d(TAG, "MainActivity#states $intent")
            }
        }
    }

    private fun keyboardIntent(state: KeyboardState) {
        val view = currentFocus
        if (view is EditText) {
            view.setText(state.number, EDITABLE)
            view.setSelection(state.number.length)
        }
    }

    private fun setSelectedCountry(countryCode: CountryCodeCurrencies, source: String) {
        Toast.makeText(
            this,
            getString(R.string.selected_country, countryCode.countryCode, source),
            LENGTH_SHORT
        ).show()

        countryDialogPicker.dismiss()
    }
}
