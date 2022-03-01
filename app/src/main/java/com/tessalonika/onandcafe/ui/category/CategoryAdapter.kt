package com.tessalonika.onandcafe.ui.category

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tessalonika.onandcafe.databinding.ItemListCategoryBinding
import com.tessalonika.onandcafe.model.Category

class CategoryAdapter(
    private val onClick: (Category) -> Unit
): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private val stocks = arrayListOf<Category>()
    
    inner class CategoryHolder(
        private val binding: ItemListCategoryBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.apply {
                tvStockName.text = category.categoryName

                if (category.categoryImage.isNotEmpty()) {
                    val categoryImage = category.categoryImage
                    val bitmap = BitmapFactory.decodeByteArray(categoryImage, 0, categoryImage.size)
                    civStock.setImageBitmap(bitmap)
                }

                itemView.setOnClickListener { onClick(category) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListCategoryBinding.inflate(inflater, parent, false)

        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(stocks[position])
    }

    override fun getItemCount(): Int = stocks.size

    fun setData(stocks: List<Category>) {
        this.stocks.clear()
        this.stocks.addAll(stocks)
        notifyDataSetChanged()
    }
}