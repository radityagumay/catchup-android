package com.radityalabs.ui.main.data.local.exchange

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val CURRENCIES_EXCHANGE_TABLE = "currencies_exchange_table"

@Entity(tableName = CURRENCIES_EXCHANGE_TABLE)
data class CurrenciesExchangeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "fromCountry") val fromCountry: String,
    @ColumnInfo(name = "toCountry") val toCountry: String,
    @ColumnInfo(name = "rates") val rates: Double
)