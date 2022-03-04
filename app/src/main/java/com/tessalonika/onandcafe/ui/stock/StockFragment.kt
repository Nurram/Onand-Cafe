package com.tessalonika.onandcafe.ui.stock

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentStockBinding
import com.tessalonika.onandcafe.ui.ViewModelFactory
import com.tessalonika.onandcafe.ui.stock.add.StockAddActivity

class StockFragment : BaseFragment<FragmentStockBinding>() {

    override fun getViewBinding(): FragmentStockBinding =
        FragmentStockBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StockAdapter {
            val i = Intent(requireActivity(), StockAddActivity::class.java)
            i.putExtra("stock", it)
            startActivity(i)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[StockViewModel::class.java]
        viewModel.getStocks()?.observe(viewLifecycleOwner) {
            if (it != null) adapter.setData(it)
        }

        binding.fabStockCategory.setOnClickListener {
            val i = Intent(requireActivity(), StockAddActivity::class.java)
            startActivity(i)
        }
    }
}