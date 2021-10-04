package com.radityalabs.ui.main.data.model

import com.radityalabs.network.model.SupportCurrenciesResponse
import com.radityalabs.ui.main.data.local.support.SupportCurrenciesEntity

data class SupportCurrencies(
    val currencies: List<Item>
) {
    data class Item(val country: String, val value: String)

    companion object {
        @JvmName("fromEntities")
        fun from(items: List<SupportCurrenciesEntity>): List<Item> {
            return items.map { entity ->
                Item(entity.country, entity.countryName)
            }
        }

        @JvmName("fromResponse")
        fun from(items: SupportCurrenciesResponse): List<Item> {
            return items.currencies.map { entity ->
                Item(entity.key, entity.value)
            }
        }

        @JvmName("fromItem")
        fun from(items: List<Item>): List<SupportCurrenciesEntity> {
            return items.map { item ->
                SupportCurrenciesEntity(item.country, item.value)
            }
        }
    }
}

