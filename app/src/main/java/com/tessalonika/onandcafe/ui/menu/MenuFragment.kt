package com.tessalonika.onandcafe.ui.menu

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentMenuBinding
import com.tessalonika.onandcafe.ui.ViewModelFactory

class MenuFragment : BaseFragment<FragmentMenuBinding>() {
    private lateinit var adapter: MenuAdapter
    private lateinit var viewModel: MenuViewModel

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
        getData(true)

        binding.apply {
            rvMenu.adapter = adapter
            rvMenu.layoutManager = LinearLayoutManager(requireContext())

            btnCoffee.setOnClickListener {
                btnCoffee.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_orange_dark))
                btnNonCoffee.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.ash))

                getData(true)
            }
            btnNonCoffee.setOnClickListener {
                btnNonCoffee.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_orange_dark))
                btnCoffee.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.ash))

                getData(false)
            }
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

    private fun getData(isCoffee: Boolean) {
        if (isCoffee) viewModel.getAll()?.observe(viewLifecycleOwner) {
            adapter.setData(it)
        } else {
            viewModel.getAllNonCoffee()?.observe(viewLifecycleOwner) {
                adapter.setData(it)
            }
        }
    }
}