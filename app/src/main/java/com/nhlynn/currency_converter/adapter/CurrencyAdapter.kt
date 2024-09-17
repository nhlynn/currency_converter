package com.nhlynn.currency_converter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nhlynn.currency_converter.databinding.CurrencyItemBinding
import com.nhlynn.currency_converter.delegate.CurrencyDelegate
import com.nhlynn.currency_converter.model.CurrencyDataVO
import com.nhlynn.currency_converter.view_holder.CurrencyViewHolder

class CurrencyAdapter(private val delegate: CurrencyDelegate) :
    RecyclerView.Adapter<CurrencyViewHolder>() {
    private var currencyList = ArrayList<CurrencyDataVO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding, delegate)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = currencyList[position]

        holder.bindData(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(currencyList: ArrayList<CurrencyDataVO>) {
        this.currencyList = currencyList
        notifyDataSetChanged()
    }
}