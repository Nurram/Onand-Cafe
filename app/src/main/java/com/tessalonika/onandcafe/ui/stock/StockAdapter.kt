package com.tessalonika.onandcafe.ui.stock

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tessalonika.onandcafe.databinding.ItemListStockBinding
import com.tessalonika.onandcafe.model.Stock

class StockAdapter(
    private val onClick: (Stock) -> Unit
): RecyclerView.Adapter<StockAdapter.StockHolder>() {

    private val stocks = arrayListOf<Stock>()
    
    inner class StockHolder(
        private val binding: ItemListStockBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(stock: Stock) {
            binding.apply {
                tvStockName.text = stock.stockName

                if (stock.StockImage.isNotEmpty()) {
                    val categoryImage = stock.StockImage
                    val bitmap = BitmapFactory.decodeByteArray(categoryImage, 0, categoryImage.size)
                    civStock.setImageBitmap(bitmap)
                }

                itemView.setOnClickListener { onClick(stock) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListStockBinding.inflate(inflater, parent, false)

        return StockHolder(binding)
    }

    override fun onBindViewHolder(holder: StockHolder, position: Int) {
        holder.bind(stocks[position])
    }

    override fun getItemCount(): Int = stocks.size

    fun setData(stocks: List<Stock>) {
        this.stocks.clear()
        this.stocks.addAll(stocks)
        notifyDataSetChanged()
    }
}