package com.radityalabs.ui.widgets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.radityalabs.R
import com.radityalabs.ui.main.presentation.model.CountryCodeCurrencies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import reactivecircus.flowbinding.common.checkMainThread
import reactivecircus.flowbinding.common.safeOffer

@ExperimentalCoroutinesApi
class CountryDialogPicker(context: Context) : BottomSheetDialog(context) {

    private val adapter = CountryAdapter()

    init {
        setContentView(R.layout.bottom_sheet_country)
        setupView()
    }

    fun submitList(list: List<CountryCodeCurrencies>) {
        adapter.postList(list)
    }

    fun countrySelectedFlow(): Flow<CountryCodeCurrencies> = callbackFlow {
        checkMainThread()
        val listener: (CountryCodeCurrencies) -> Unit = {
            safeOffer(it)
        }
        adapter.setSelectionListener { listener(it) }
        awaitClose { }
    }

    private fun setupView() {
        val rv: RecyclerView = findViewById(R.id.rvCountry)!!
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }

    private class CountryAdapter : ListAdapter<CountryCodeCurrencies, CountryViewHolder>(diffCallback) {

        private val countries = mutableListOf<CountryCodeCurrencies>()
        private lateinit var callback: (CountryCodeCurrencies) -> Unit

        fun postList(list: List<CountryCodeCurrencies>) {
            countries.clear()
            countries.addAll(list)
            super.submitList(ArrayList(list))
        }

        fun setSelectionListener(callback: (CountryCodeCurrencies) -> Unit) {
            this.callback = callback
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
            return LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_country, parent, false)
                .let { view ->
                    CountryViewHolder(view) { position ->
                        callback(countries[position])
                    }
                }
        }

        override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
            holder.bind(countries[position])
        }

        companion object {
            val diffCallback = object : DiffUtil.ItemCallback<CountryCodeCurrencies>() {
                override fun areItemsTheSame(
                    oldItem: CountryCodeCurrencies,
                    newItem: CountryCodeCurrencies
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: CountryCodeCurrencies,
                    newItem: CountryCodeCurrencies
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }

    private class CountryViewHolder(
        view: View,
        private val callback: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val tvCountry by lazy { view.findViewById<TextView>(R.id.tvCountry) }

        init {
            tvCountry.setOnClickListener {
                callback(adapterPosition)
            }
        }

        fun bind(data: CountryCodeCurrencies) {
            tvCountry.text = data.country
        }
    }
}