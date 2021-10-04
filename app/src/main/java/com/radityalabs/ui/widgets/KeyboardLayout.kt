package com.radityalabs.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.radityalabs.R
import com.radityalabs.databinding.KeyboardLayoutBinding
import com.radityalabs.ui.main.presentation.MainIntent
import com.radityalabs.ui.main.presentation.MainIntent.KeyboardIntent
import com.radityalabs.ui.main.presentation.MainIntent.KeyboardIntent.*
import com.radityalabs.ui.widgets.CurrencyFormatter.priceToString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import reactivecircus.flowbinding.android.view.clicks

@ExperimentalCoroutinesApi
class KeyboardLayout : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.keyboard_layout, this, true)
    }

    fun keyboardFlow(): Flow<MainIntent> {
        val keyboard = KeyboardLayoutBinding.bind(this)
        val btn1 = keyboard.btn1.clicks().map { KeyedIntent("1") }
        val btn2 = keyboard.btn2.clicks().map { KeyedIntent("2") }
        val btn3 = keyboard.btn3.clicks().map { KeyedIntent("3") }
        val btn4 = keyboard.btn4.clicks().map { KeyedIntent("4") }
        val btn5 = keyboard.btn5.clicks().map { KeyedIntent("5") }
        val btn6 = keyboard.btn6.clicks().map { KeyedIntent("6") }
        val btn7 = keyboard.btn7.clicks().map { KeyedIntent("7") }
        val btn8 = keyboard.btn8.clicks().map { KeyedIntent("8") }
        val btn9 = keyboard.btn9.clicks().map { KeyedIntent("9") }
        val btn0 = keyboard.btn0.clicks().map { KeyedIntent("0") }
        val btn000 = keyboard.btn000.clicks().map { KeyedIntent("000") }
        val btnDel = keyboard.btnDel.clicks().map { DeleteIntent }
        val btnConvert = keyboard.btnConvert.clicks().throttleFirst(1000).map { ConvertIntent }

        val numbers = StringBuilder()
        val emission = merge(
            btn1, btn2, btn3,
            btn4, btn5, btn6,
            btn7, btn8, btn9,
            btn0, btn000, btnDel, btnConvert
        ).map { intent ->
            when (intent) {
                is KeyedIntent -> keyedboardIntent(intent, numbers)
                is DeleteIntent -> deleteIntent(numbers)
                is ConvertIntent -> ConvertIntent
                else -> Initial
            }
        }
        return emission
    }

    private fun keyedboardIntent(
        intent: KeyedIntent,
        numbers: StringBuilder
    ): KeyboardIntent {
        val number = intent.number
        numbers.append(number)
        return KeyedIntent(numbers.toString().toDouble().let(::priceToString))
    }

    private fun deleteIntent(numbers: StringBuilder) = if (numbers.isNotEmpty()) {
        numbers.deleteCharAt(numbers.length - 1)
        if (numbers.isNotEmpty()) {
            KeyedIntent(numbers.toString().toDouble().let(::priceToString))
        } else {
            KeyedIntent("")
        }
    } else {
        KeyedIntent("")
    }
}
