package com.tessalonika.onandcafe.ui.menu.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.base.showToast
import com.tessalonika.onandcafe.databinding.FragmentOrderBinding
import com.tessalonika.onandcafe.model.Menu
import com.tessalonika.onandcafe.model.Order
import com.tessalonika.onandcafe.model.OrderMenuCrossRef
import com.tessalonika.onandcafe.model.OrderWithMenu
import com.tessalonika.onandcafe.ui.ViewModelFactory
import com.tessalonika.onandcafe.ui.menu.MenuViewModel
import java.util.*

class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    override fun getViewBinding(): FragmentOrderBinding =
        FragmentOrderBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]

        val adapter = OrderAdapter(requireContext())
        val selectedMenus = arrayListOf<Menu>()

        viewModel.getOnError().observe(viewLifecycleOwner) {
            showToast(requireContext(), it, Toast.LENGTH_SHORT)
        }
        viewModel.getIsSuccess().observe(viewLifecycleOwner) {
            if (it != null) Navigation
                .findNavController(view)
                .navigate(R.id.action_menu_category_to_menu_history)
        }

        binding.apply {
            rvMenus.adapter = adapter
            rvMenus.layoutManager = LinearLayoutManager(requireContext())

            btnAddMenu.setOnClickListener {
                Navigation
                    .findNavController(it)
                    .currentBackStackEntry?.
                    savedStateHandle?.
                    getLiveData<List<Menu>>("menu")?.observe(viewLifecycleOwner) { menus ->
                        selectedMenus.addAll(menus)

                        menus.forEach { menu ->
                            adapter.addData(menu)
                        }
                    }
            }
            btnOrder.setOnClickListener {
                val order = Order(
                    0,
                    "Tunai",
                    Date(),
                    adapter.getTotalPrice(),
                    tilTableNo.editText.toString()
                )


                selectedMenus.forEach { menu ->
                    viewModel.insertOrder(order, menu.menuId)
                }

                val navController = Navigation.findNavController(it)
                val id = navController.currentDestination?.id
                navController.popBackStack(id!!,true)
                navController.navigate(id)
            }
        }
    }
}