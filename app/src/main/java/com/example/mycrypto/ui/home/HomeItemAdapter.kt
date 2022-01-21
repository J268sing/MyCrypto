package com.example.mycrypto.ui.home

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycrypto.data.CryptocurrencyItemHome
import com.example.mycrypto.databinding.LayoutListItemHomeBinding
import com.example.mycrypto.ui.utilities.SpannableValueColorStyle
import com.example.mycrypto.ui.utilities.roundValue
import com.example.mycrypto.ui.utilities.ValueType
import com.example.mycrypto.ui.utilities.getSpannableValueStyled

class HomeItemAdapter : RecyclerView.Adapter<HomeItemAdapter.BindingViewHolder>() {

    private lateinit var dataList: List<CryptocurrencyItemHome>

    fun setData(newDataList: List<CryptocurrencyItemHome>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutListItemHomeBinding.inflate(inflater, parent, false)
        return BindingViewHolder(binding)
    }
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class BindingViewHolder(var binding: LayoutListItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptocurrencyItemHome: CryptocurrencyItemHome) {

            binding.cryptocurrencyBinding = cryptocurrencyItemHome
            binding.itemRankingCrypto.text = String.format("${cryptocurrencyItemHome.rank}")
            binding.itemAmountSymbol.text = String.format("${roundValue(cryptocurrencyItemHome.amount, ValueType.Crypto)} ${cryptocurrencyItemHome.symbol}")
            binding.itemPrice.text = String.format("${roundValue(cryptocurrencyItemHome.price, ValueType.Fiat)}")
            binding.itemAmountCad.text = String.format("${roundValue(cryptocurrencyItemHome.amountFiat, ValueType.Fiat)}")
            binding.itemPricePercentChange1h7d.text = SpannableStringBuilder(getSpannableValueStyled(binding.root.context, cryptocurrencyItemHome.pricePercentChange1h, SpannableValueColorStyle.Foreground, ValueType.Percent, "", "%"))
                    .append("/").append(getSpannableValueStyled(binding.root.context, cryptocurrencyItemHome.pricePercentChange7d, SpannableValueColorStyle.Foreground, ValueType.Fiat, "", "%" ))
            binding.itemPricePercentChange24h.text = getSpannableValueStyled(binding.root.context, cryptocurrencyItemHome.pricePercentChange24h, SpannableValueColorStyle.Foreground, ValueType.Percent, "", "%")
            binding.itemAmountCadChange24h.text = getSpannableValueStyled(binding.root.context, cryptocurrencyItemHome.amountFiatChange24h, SpannableValueColorStyle.Foreground, ValueType.Percent, "", " ${cryptocurrencyItemHome.price}")
            binding.executePendingBindings()
        }
    }
}