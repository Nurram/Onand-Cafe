package com.tessalonika.onandcafe.ui.menu

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentMenuBinding
import com.tessalonika.onandcafe.ui.ViewModelFactory
import com.tessalonika.onandcafe.ui.data.MenuViewModel
import com.tessalonika.onandcafe.ui.stock.StockViewModel

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    override fun getViewBinding(): FragmentMenuBinding =
        FragmentMenuBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MenuAdapter(requireContext()) {
            val bundle = Bundle()
            bundle.putParcelable("menu", it)

            Navigation
                .findNavController(view)
                .navigate(R.id.action_menuFragment_to_menuAddFragment, bundle)
        }

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[MenuViewModel::class.java]
        viewModel.getAll()?.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.apply {
            rvMenu.adapter = adapter
            rvMenu.layoutManager = LinearLayoutManager(requireContext())

            fabMenuAdd.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelableArrayList("menus", adapter.getData())

                Navigation
                    .findNavController(it)
                    .previousBackStackEntry?.
                    savedStateHandle?.
                    set("menu", adapter.getData())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_menuFragment_to_menuAddFragment)
        }

        return true
    }
}