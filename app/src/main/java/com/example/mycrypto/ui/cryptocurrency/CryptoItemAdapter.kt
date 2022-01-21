package com.example.mycrypto.ui.home


import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.mycrypto.R
import com.example.mycrypto.data.CryptocurrencyItem
import com.example.mycrypto.data.CryptocurrencyItemHome
import com.example.mycrypto.ui.cryptocurrency.CellClickListener
//import com.example.mycrypto.databinding.LayoutListItemCryptoBinding
import com.example.mycrypto.ui.utilities.SpannableValueColorStyle
import kotlinx.android.synthetic.main.layout_list_item_crypto.view.*

 class CryptoItemAdapter(private val cellClickListener: CellClickListener) : RecyclerView.Adapter<CryptoItemAdapter.itemViewHolder>()
{

    private lateinit var data: List<CryptocurrencyItemHome>

    fun setData(newDataList: List<CryptocurrencyItemHome>) {
        data = newDataList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {

       // val inflater = LayoutInflater.from(parent.context)
      //  val binding = LayoutListItemCryptoBinding.inflate(inflater, parent, false)

     //   return BindingViewHolder(binding)
        return itemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item_crypto, parent, false)
        )
    }


    //override fun onBindViewHolder(holder: BindingViewHolder, position: Int) = holder.bind(data[position])

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
      //  holder.itemView.setOnClickListener(cellClickListener.onCellClickListener())
        when(holder) {
            is itemViewHolder -> {
                holder.bind(data.get(position))
            }


        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getItem(position: Int): CryptocurrencyItemHome{
        return data[position]
    }


   /* inner class BindingViewHolder(var binding: LayoutListItemCryptoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptocurrencyItemHome: CryptocurrencyItemHome) {
            binding.cryptocurrencyBindingCrypto = cryptocurrencyItemHome
            binding.itemNameCrypto.text = cryptocurrencyItemHome.name
            binding.itemRankingCrypto.text = cryptocurrencyItemHome.rank.toString()
            binding.itemSymbolCrypto.text = cryptocurrencyItemHome.symbol
            binding.executePendingBindings()
        }
    }*/

   inner class itemViewHolder
    constructor(
            itemView: View
           // cellClickListener: CellClickListener
    ): RecyclerView.ViewHolder(itemView),View.OnClickListener{

        /*   val item_image = itemView.item_image
           val item_title = itemView.item_title
           val item_author = itemView.item_author*/


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if(adapterPosition != RecyclerView.NO_POSITION) {
                cellClickListener.onCellClickListener(adapterPosition)
            }
        }

        val itemName = itemView.item_name_crypto
        val itemRanking = itemView.item_ranking_crypto
        val itemSymbol = itemView.item_symbol_crypto
    //    val ans = itemView.setOnClickListener(cellClickListener.onCellClickListener())
        fun bind(cryptocurrencyItem: CryptocurrencyItemHome){

            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)

            /*  Glide.with(itemView.context)
                  .applyDefaultRequestOptions(requestOptions)
                  .load(cryptoItemHome.image)
                  .into(item_image)
              item_title.setText(cryptoItemHome.title)
              item_author.setText(cryptoItemHome.abbrevation)*/
            itemName?.setText(cryptocurrencyItem.name)
            itemRanking?.setText(String.format("${cryptocurrencyItem.rank}"))
            itemSymbol?.setText(cryptocurrencyItem.symbol)
        }


    }

}