package com.tessalonika.onandcafe.ui.table

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentTableBinding
import com.tessalonika.onandcafe.ui.ViewModelFactory

class TableFragment : BaseFragment<FragmentTableBinding>() {
    private lateinit var viewModel: TableViewModel

    override fun getViewBinding(): FragmentTableBinding =
        FragmentTableBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TableAdapter(requireContext()) { showDialog(it.id) }

        binding.rvTables.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvTables.adapter = adapter

        val factory = ViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[TableViewModel::class.java]
        viewModel.getTables()?.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) viewModel.generateTable()
            else adapter.setData(it)
        }

    }

    private fun showDialog(id: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        builder.setTitle(getString(R.string.warning))
        builder.setMessage(getString(R.string.set_unoccupied))
        builder.setPositiveButton("Yes") { _, _ -> viewModel.setUnOccupied(id) }
        builder.show()
    }
}