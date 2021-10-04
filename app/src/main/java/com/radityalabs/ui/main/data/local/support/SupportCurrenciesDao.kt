package com.radityalabs.ui.main.data.local.support

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SupportCurrenciesDao {
    @Query("SELECT * FROM $SUPPORT_CURRENCIES_TABLE")
    suspend fun getSupportCurrencies(): List<SupportCurrenciesEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: List<SupportCurrenciesEntity>)
}