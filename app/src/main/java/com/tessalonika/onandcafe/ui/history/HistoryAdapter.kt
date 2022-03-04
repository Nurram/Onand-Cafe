package com.tessalonika.onandcafe.ui.history

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tessalonika.onandcafe.model.OrderMenuCrossRef

class HistoryAdapter() : ListAdapter<OrderMenuCrossRef, RecyclerView.ViewHolder>(DIFF_UTIL) {
    var date: Date? = null

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<OrderMenuCrossRef>() {
            override fun areItemsTheSame(oldItem: OrderMenuCrossRef, newItem: OrderMenuCrossRef): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: OrderMenuCrossRef, newItem: OrderMenuCrossRef): Boolean =
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
        val inflater = LayoutInflater.from(context)
        val binding: ViewBinding

        if (p1 == 0) {
            binding = ItemRowBinding.inflate(inflater, p0, false)
            return MainHolder(binding)
        }

        binding = ItemDateBinding.inflate(inflater, p0, false)
        return DateHolder(binding)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val data = getItem(p1)

        if (data != null) {
            if (p0.itemViewType == 0) {
                p0 as MainHolder
                p0.bind(data, clickUtils)
            } else {
                p0 as DateHolder
                p0.bind(data.date!!)
            }
        }
    }

    inner class MainHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var record: OrderMenuCrossRef

        fun bind(record: OrderMenuCrossRef, clickUtils: (OrderMenuCrossRef, View) -> Unit) {
            this.record = record

            binding.apply {
                itemTitle.text = record.judul
                itemDesc.text = record.note
                itemView.setOnClickListener { clickUtils(record, binding.root) }

                if (record.note.isNotEmpty()) itemDesc.VISIBLE()

                when (record.description) {
                    AddDataActivity.INCOME -> {
                        itemUang.text =
                            context.getString(R.string.plus_value, convertAndFormat(record.total))
                        itemUang.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                    }
                    AddDataActivity.EXPENSE -> {
                        itemUang.text =
                            context.getString(R.string.minus_value, convertAndFormat(record.total))
                        itemUang.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
                    }
                    else -> {
                        itemUang.text =
                            context.getString(R.string.minus_value, convertAndFormat(record.total))
                        itemUang.setTextColor(ContextCompat.getColor(context, R.color.colorGreen))
                    }
                }
            }
        }
    }

    inner class DateHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Date) {
            binding.itemDate.text = DateUtil.formatDate(date)
        }
    }
}