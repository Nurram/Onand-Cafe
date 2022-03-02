package com.tessalonika.onandcafe.ui.stock.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.base.enable
import com.tessalonika.onandcafe.base.showToast
import com.tessalonika.onandcafe.base.visible
import com.tessalonika.onandcafe.databinding.FragmentStockAddBinding
import com.tessalonika.onandcafe.model.Stock
import com.tessalonika.onandcafe.ui.ViewModelFactory
import com.tessalonika.onandcafe.ui.stock.StockViewModel

class StockAddFragment : BaseFragment<FragmentStockAddBinding>() {
    private lateinit var viewModel: StockViewModel

    override fun getViewBinding(): FragmentStockAddBinding =
        FragmentStockAddBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[StockViewModel::class.java]

        val stock: Stock? = arguments?.getParcelable("stock")
        initUpdateUI(stock)

        binding.apply {
            btnStockSave.setOnClickListener { saveStock(it) }
        }
    }

    private fun initUpdateUI(stock: Stock?) {
        binding.apply {

            if (stock != null) {
                tilStockName.editText?.setText(stock.stockName)
                tilStockId.editText?.let { 
                    it.setText(stock.id)
                    it.enable(false)
                }

                tilStockMetric.editText?.setText(stock.stockMetric)
                tilStockInitial.editText?.setText(stock.stockInitialValue)
                tilStockIn.editText?.setText(stock.stockIn)
                tilStockOut.editText?.setText(stock.stockOut)
                tilStockTotal.editText?.setText(stock.stockTotal)
                
                btnStockDelete.visible(true)
                btnStockDelete.setOnClickListener {
                    viewModel.deleteStock(stock)
                    Navigation.findNavController(it).popBackStack()
                }
            }
        }
    }

    private fun saveStock(view: View) {
        binding.apply {
            val stockNameEt = tilStockName.editText?.text
            val stockIdEt = tilStockId.editText?.text
            val stockMetricEt = tilStockMetric.editText?.text
            val stockInitialValueEt = tilStockInitial.editText?.text
            val stockInEt = tilStockIn.editText?.text
            val stockOutEt = tilStockOut.editText?.text
            val stockTotalEt = tilStockTotal.editText?.text

            if (stockNameEt.isNullOrEmpty()
                || stockIdEt.isNullOrEmpty()
                || stockMetricEt.isNullOrEmpty()
                || stockInitialValueEt.isNullOrEmpty()
                || stockInEt.isNullOrEmpty()
                || stockOutEt.isNullOrEmpty()
                || stockTotalEt.isNullOrEmpty())
                showToast(requireContext(), getString(R.string.please_fill), Toast.LENGTH_SHORT)
            else {
                val stock = Stock(
                    stockIdEt.toString().toInt(),
                    stockNameEt.toString(),
                    stockMetricEt.toString(),
                    stockInitialValueEt.toString().toInt(),
                    stockInEt.toString().toInt(),
                    stockOutEt.toString().toInt(),
                    stockTotalEt.toString().toInt()
                )

                viewModel.insertStock(stock)
                Navigation.findNavController(view).popBackStack()
            }
        }
    }
}