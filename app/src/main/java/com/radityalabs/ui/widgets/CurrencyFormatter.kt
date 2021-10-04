package com.radityalabs.ui.widgets

import java.text.DecimalFormat

object CurrencyFormatter {
    private fun priceWithDecimal(price: Double): String {
        val formatter = DecimalFormat("###,###,###.00")
        return formatter.format(price)
    }

    private fun priceWithoutDecimal(price: Double): String {
        val formatter = DecimalFormat("###,###,###.##")
        return formatter.format(price)
    }

    fun priceToString(price: Double): String {
        val toShow = priceWithoutDecimal(price)
        return if (toShow.indexOf(".") > 0) {
            priceWithDecimal(price)
        } else {
            priceWithoutDecimal(price)
        }
    }
}