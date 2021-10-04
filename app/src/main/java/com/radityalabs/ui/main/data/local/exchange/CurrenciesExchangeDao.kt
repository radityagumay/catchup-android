package com.radityalabs.ui.main.data.local.exchange

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrenciesExchangeDao {
    @Query("SELECT * FROM $CURRENCIES_EXCHANGE_TABLE")
    suspend fun getCurrenciesExchange(): List<CurrenciesExchangeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: List<CurrenciesExchangeEntity>)
}