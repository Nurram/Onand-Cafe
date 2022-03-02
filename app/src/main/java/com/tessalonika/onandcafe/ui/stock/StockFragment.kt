package com.tessalonika.onandcafe.ui.stock

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentStockBinding
import com.tessalonika.onandcafe.ui.ViewModelFactory

class StockFragment : BaseFragment<FragmentStockBinding>() {

    override fun getViewBinding(): FragmentStockBinding =
        FragmentStockBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StockAdapter {
            val bundle = Bundle()
            bundle.putParcelable("stock", it)

            Navigation
                .findNavController(view)
                .navigate(R.id.action_menu_category_to_categoryAddFragment, bundle)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[StockViewModel::class.java]
        viewModel.getStocks()?.observe(viewLifecycleOwner) {
            if (it != null) adapter.setData(it)
        }

        binding.fabStockCategory.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_menu_category_to_categoryAddFragment)
        }
    }
}