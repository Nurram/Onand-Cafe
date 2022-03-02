package com.tessalonika.onandcafe.ui.menu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.databinding.ItemListMenuBinding
import com.tessalonika.onandcafe.databinding.ItemListStockBinding
import com.tessalonika.onandcafe.model.Menu

class MenuAdapter(
    private val context: Context,
    private val onClick: (Menu) -> Unit
): RecyclerView.Adapter<MenuAdapter.MenuHolder>() {

    private val menus = arrayListOf<Menu>()
    
    inner class MenuHolder(
        private val binding: ItemListMenuBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            binding.apply {
                tvName.text = menu.name
                tvPrice.text = menu.price.toString()
                ivEdit.setOnClickListener { onClick(menu) }

                itemView.setOnClickListener {
                    menu.isSelected = !menu.isSelected

                    if (menu.isSelected) {
                        itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
                    } else {
                        itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    }

                    Log.d("TAG", "$menus")
                    Log.d("TAG", "${menus.filter { it.isSelected }}")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListMenuBinding.inflate(inflater, parent, false)

        return MenuHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(menus[position])
    }

    override fun getItemCount(): Int = menus.size

    fun setData(menus: List<Menu>) {
        this.menus.clear()
        this.menus.addAll(menus)
        notifyDataSetChanged()
    }

    fun getData(): List<Menu> = menus
}