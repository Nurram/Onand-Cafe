package com.tessalonika.onandcafe.ui.stock.add

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import java.io.ByteArrayOutputStream

class StockAddFragment : BaseFragment<FragmentStockAddBinding>() {
    private lateinit var viewModel: StockViewModel

    private var imageByte = byteArrayOf()
    private var stock: Stock? = null

    override fun getViewBinding(): FragmentStockAddBinding =
        FragmentStockAddBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[StockViewModel::class.java]

        stock = arguments?.getParcelable("stock")
        initUpdateUI(stock)

        binding.apply {
            ibStockImage.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                val chooser = Intent.createChooser(intent, "Select Picture")
                result.launch(chooser)
            }

            btnStockSave.setOnClickListener { saveStock(it) }
        }
    }

    private fun initUpdateUI(stock: Stock?) {
        binding.apply {

            if (stock != null) {

                if (stock.StockImage.isNotEmpty()) {
                    imageByte = stock.StockImage
                    val stockImage = stock.StockImage
                    val bitmap = BitmapFactory.decodeByteArray(stockImage, 0, stockImage.size)
                    ibStockImage.setImageBitmap(bitmap)
                }

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
            val stockName = tilStockName.editText?.text.toString()
            val stockId = tilStockId.editText?.text.toString().toInt()
            val stockMetric = tilStockMetric.editText?.text.toString()
            val stockInitialValue = tilStockInitial.editText?.text.toString().toInt()
            val stockIn = tilStockIn.editText?.text.toString().toInt()
            val stockOut = tilStockOut.editText?.text.toString().toInt()
            val stockTotal = tilStockTotal.editText?.text.toString().toInt()

            if (stockName.isEmpty())
                showToast(requireContext(), getString(R.string.please_fill), Toast.LENGTH_SHORT)
            else {

                if (stock == null) stock = Stock(
                    stockId,
                    stockName,
                    stockMetric,
                    stockInitialValue,
                    stockIn,
                    stockOut,
                    stockTotal,
                    imageByte
                )
                else {
                    stock!!.StockImage = imageByte
                    stock!!.stockName = stockName
                }

                viewModel.insertStock(stock!!)
                Navigation.findNavController(view).popBackStack()
            }
        }
    }
    
    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                if (it.data!!.data != null) {
                    val inputStream = requireActivity().contentResolver.openInputStream(it.data!!.data!!)
                    if (inputStream != null) {
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.ibStockImage.setImageBitmap(bitmap)

                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        imageByte = stream.toByteArray()
                    }
                }
            }
        }
}