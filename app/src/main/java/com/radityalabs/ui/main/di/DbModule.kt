package com.radityalabs.ui.main.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.radityalabs.ui.main.data.local.exchange.CurrenciesExchangeDao
import com.radityalabs.ui.main.data.local.exchange.CurrenciesExchangeEntity
import com.radityalabs.ui.main.data.local.support.SupportCurrenciesDao
import com.radityalabs.ui.main.data.local.support.SupportCurrenciesEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DbModule {
    @Provides
    fun provideSupportCurrenciesDao(context: Context): SupportCurrenciesDao {
        return AppDatabase.getDatabase(context).supportCurrenciesDao()
    }

    @Provides
    fun provideCurrenciesExchangeDao(context: Context): CurrenciesExchangeDao {
        return AppDatabase.getDatabase(context).currenciesExchangeDao()
    }
}

@Database(
    entities = [
        SupportCurrenciesEntity::class,
        CurrenciesExchangeEntity::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun supportCurrenciesDao(): SupportCurrenciesDao
    abstract fun currenciesExchangeDao(): CurrenciesExchangeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}