package com.tessalonika.onandcafe.ui.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentHistoryBinding
import com.tessalonika.onandcafe.model.Order
import com.tessalonika.onandcafe.ui.ViewModelFactory

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    override fun getViewBinding(): FragmentHistoryBinding =
        FragmentHistoryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HistoryAdapter(requireContext())
        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]
        viewModel.getAll()
        viewModel.getIsSuccess().observe(viewLifecycleOwner) {
            val data = it.dropWhile { order -> order.type == 1 }
            val mappedData = viewModel.mapData(data as ArrayList<Order>)
            adapter.submitList(mappedData)
        }

        binding.apply {
            rvHistory.adapter = adapter
            rvHistory.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}