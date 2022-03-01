package com.tessalonika.onandcafe.ui.category

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.db.daos.CategoryDao
import com.tessalonika.onandcafe.model.Category
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryDao: CategoryDao?
): BaseViewModel<List<Category>>() {

    fun getCategories() = categoryDao?.getCategories()

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            categoryDao?.insertCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryDao?.deleteCategory(category)
        }
    }
}