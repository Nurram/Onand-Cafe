package com.tessalonika.onandcafe.ui.table

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.db.daos.TableDao
import com.tessalonika.onandcafe.model.Table
import kotlinx.coroutines.launch

class TableViewModel(
    private val tableDao: TableDao?
): BaseViewModel<List<Table>>() {
    
    fun getTables() {
        isLoading.postValue(true)
        viewModelScope.launch {
            val tables = tableDao?.getTables()
            
            if (tables.isNullOrEmpty()) {
                generateTable()
            } else {
                isSuccess.postValue(tables)
                isLoading.postValue(false)   
            }
        }
    }
    
    private suspend fun generateTable() {
        val tables = arrayListOf<Table>()
        
        for (i: Int in 1 until 8) {
            val table = Table(i, "Meja $i", false)
            tables.add(table)
        }

        tableDao?.insertTables(tables)
        isSuccess.postValue(tables)
        isLoading.postValue(false)
    }
}