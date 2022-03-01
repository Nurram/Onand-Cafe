package com.tessalonika.onandcafe.ui.category.add

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.base.showToast
import com.tessalonika.onandcafe.base.visible
import com.tessalonika.onandcafe.databinding.FragmentCategoryAddBinding
import com.tessalonika.onandcafe.model.Category
import com.tessalonika.onandcafe.ui.ViewModelFactory
import com.tessalonika.onandcafe.ui.category.CategoryViewModel
import java.io.ByteArrayOutputStream

class CategoryAddFragment : BaseFragment<FragmentCategoryAddBinding>() {
    private var imageByte = byteArrayOf()
    private var category: Category? = null

    override fun getViewBinding(): FragmentCategoryAddBinding =
        FragmentCategoryAddBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]

        category = arguments?.getParcelable("category")
        if (category != null) {
            if (category!!.categoryImage.isNotEmpty()) {
                imageByte = category!!.categoryImage
                val categoryImage = category!!.categoryImage
                val bitmap = BitmapFactory.decodeByteArray(categoryImage, 0, categoryImage.size)
                binding.ibCategoryImage.setImageBitmap(bitmap)
            }

            binding.tilCategoryName.editText?.setText(category?.categoryName)
            binding.btnCategoryDelete.visible(true)
            binding.btnCategoryDelete.setOnClickListener {
                viewModel.deleteCategory(category!!)
                Navigation.findNavController(it).popBackStack()
            }
        }

        binding.apply {
            ibCategoryImage.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                val chooser = Intent.createChooser(intent, "Select Pictures")
                result.launch(chooser)
            }
            btnCategorySave.setOnClickListener {
                val categoryName = tilCategoryName.editText?.text.toString()

                if (categoryName.isEmpty())
                    showToast(requireContext(), getString(R.string.please_fill), Toast.LENGTH_SHORT)
                else {
                    if (category == null) category = Category(0, categoryName, imageByte)
                    else {
                        category!!.categoryImage = imageByte
                        category!!.categoryName = categoryName
                    }

                    viewModel.insertCategory(category!!)
                    Navigation.findNavController(it).popBackStack()
                }
            }
        }
    }

    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                if (it.data!!.data != null) {
                    val inputStream = requireActivity().contentResolver.openInputStream(it.data!!.data!!)
                    if (inputStream != null) {
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.ibCategoryImage.setImageBitmap(bitmap)

                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        imageByte = stream.toByteArray()
                    }
                }
            }
        }
}