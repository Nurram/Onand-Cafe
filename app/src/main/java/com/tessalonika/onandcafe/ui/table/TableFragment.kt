package com.tessalonika.onandcafe.ui.table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentTableBinding
import com.tessalonika.onandcafe.ui.HomeViewModel
import com.tessalonika.onandcafe.ui.ViewModelFactory

class TableFragment : BaseFragment<FragmentTableBinding>() {

    override fun getViewBinding(): FragmentTableBinding =
        FragmentTableBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TableAdapter(requireContext())
        binding.rvTables.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvTables.adapter = adapter

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[TableViewModel::class.java]
        viewModel.getTables()
        viewModel.getIsSuccess().observe(viewLifecycleOwner) { adapter.setData(it) }

    }
}