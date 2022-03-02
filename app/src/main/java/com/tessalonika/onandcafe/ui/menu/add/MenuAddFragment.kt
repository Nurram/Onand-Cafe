package com.tessalonika.onandcafe.ui.menu.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.base.showToast
import com.tessalonika.onandcafe.databinding.FragmentMenuAddBinding
import com.tessalonika.onandcafe.model.Menu
import com.tessalonika.onandcafe.ui.ViewModelFactory
import com.tessalonika.onandcafe.ui.data.MenuViewModel
import com.tessalonika.onandcafe.ui.stock.StockViewModel

class MenuAddFragment : BaseFragment<FragmentMenuAddBinding>() {
    private lateinit var viewModel: MenuViewModel

    override fun getViewBinding(): FragmentMenuAddBinding =
        FragmentMenuAddBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[MenuViewModel::class.java]

        val menu: Menu? = arguments?.getParcelable("menu")
        binding.apply {
            btnMenuSave.setOnClickListener { saveData(it) }
            btnMenuDelete.setOnClickListener {
                if(menu != null) viewModel.delete(menu)
            }
        }
    }

    private fun saveData(view: View) {
        binding.apply {
            val categoryEt = tilMenuCategory.editText?.text
            val nameEt = tilMenuName.editText?.text
            val descEt = tilMenuDesc.editText?.text
            val priceEt = tilMenuPrice.editText?.text

            if (categoryEt.isNullOrEmpty() ||
                    nameEt.isNullOrEmpty() ||
                    descEt.isNullOrEmpty() ||
                    priceEt.isNullOrEmpty()) {
                showToast(requireContext(), getString(R.string.please_fill), Toast.LENGTH_SHORT)
            } else {
                viewModel.insert(Menu(
                    0,
                    categoryEt.toString(),
                    nameEt.toString(),
                    descEt.toString(),
                    priceEt.toString().toLong()
                ))

                Navigation.findNavController(view).popBackStack()
            }
        }
    }
}