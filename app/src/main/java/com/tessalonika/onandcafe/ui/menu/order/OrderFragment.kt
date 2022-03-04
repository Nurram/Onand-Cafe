package com.tessalonika.onandcafe.ui.menu.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentOrderBinding
import com.tessalonika.onandcafe.model.Menu

class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    override fun getViewBinding(): FragmentOrderBinding =
        FragmentOrderBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnAddMenu.setOnClickListener {
                Navigation
                    .findNavController(it)
                    .currentBackStackEntry?.
                    savedStateHandle?.
                    getLiveData<List<Menu>>("menu")?.observe(viewLifecycleOwner) {

                    }
            }
        }
    }
}