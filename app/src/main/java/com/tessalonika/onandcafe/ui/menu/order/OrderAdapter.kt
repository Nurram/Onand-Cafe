package com.tessalonika.onandcafe.ui.menu.order

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.databinding.ItemListOrderBinding
import com.tessalonika.onandcafe.model.Menu

class OrderAdapter(
    private val context: Context,
    private val onClick: (Menu) -> Unit
): RecyclerView.Adapter<OrderAdapter.OrderHolder>() {
    private val orders = arrayListOf<Menu>()

    inner class OrderHolder(
        private val binding: ItemListOrderBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu) {
            binding.apply {
                tvName.text = menu.name

                if (menu.name.length > 1) {
                    tvStockInitial.text = menu.name.slice(0..2)
                } else {
                    tvStockInitial.text = menu.name.slice(0..1)
                }

                tvPrice.text = context.getString(R.string.rp,  menu.price.toString())

                ibAdd.setOnClickListener { changeQty(1) }
                ibReduce.setOnClickListener { changeQty(-1) }
                ibDelete.setOnClickListener { removeData(adapterPosition) }
                Log.d("TAG", "$adapterPosition, $layoutPosition")
            }
        }

        private fun changeQty(value: Int) {
            var qty = binding.tvQty.text.toString().toInt()
            qty += value

            binding.tvQty.text = qty.toString()
        }

        private fun removeData(position: Int) {
            orders.removeAt(position)
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListOrderBinding.inflate(inflater, parent, false)

        return OrderHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size
}