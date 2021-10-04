package com.radityalabs.ui.main.data.local.support

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val SUPPORT_CURRENCIES_TABLE = "support_currencies_table"

@Entity(tableName = SUPPORT_CURRENCIES_TABLE)
data class SupportCurrenciesEntity(
    @PrimaryKey @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "countryName") val countryName: String
)