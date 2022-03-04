package com.tessalonika.onandcafe.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tessalonika.onandcafe.base.DateUtil
import com.tessalonika.onandcafe.databinding.ItemListDateBinding
import com.tessalonika.onandcafe.databinding.ItemListHistoryBinding
import com.tessalonika.onandcafe.model.OrderWithMenu
import java.util.*

class HistoryAdapter() : ListAdapter<OrderWithMenu, RecyclerView.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<OrderWithMenu>() {
            override fun areItemsTheSame(oldItem: OrderWithMenu, newItem: OrderWithMenu): Boolean =
                oldItem.order.orderId == newItem.order.orderId

            override fun areContentsTheSame(oldItem: OrderWithMenu, newItem: OrderWithMenu): Boolean =
                oldItem == newItem

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) != null) {
            return getItem(position).type
        }

        return 0
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val binding: ViewBinding

        if (p1 == 0) {
            binding = ItemListHistoryBinding.inflate(inflater, p0, false)
            return MainHolder(binding)
        }

        binding = ItemListDateBinding.inflate(inflater, p0, false)
        return DateHolder(binding)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val data = getItem(p1)

        if (data != null) {
            if (p0.itemViewType == 0) {
                p0 as MainHolder
                p0.bind(data)
            } else {
                p0 as DateHolder
                p0.bind(data.order.orderDate)
            }
        }
    }

    inner class MainHolder(private val binding: ItemListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderWithMenu1: OrderWithMenu) {
            binding.apply {
                val order = orderWithMenu1.order
                tvDate.text = order.orderDate.toString()
                tvId.text = order.orderId.toString()
                tvPayment.text = order.paymentType
                tvPriceTotal.text = order.totalPrice.toString()
                tvTable.text = order.tableNo
            }
        }
    }

    inner class DateHolder(private val binding: ItemListDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Date) {
            binding.itemDate.text = DateUtil.formatDate(date)
        }
    }
}