package com.tessalonika.onandcafe.ui.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.databinding.FragmentContactBinding

class ContactFragment : BaseFragment<FragmentContactBinding>() {

    override fun getViewBinding(): FragmentContactBinding =
        FragmentContactBinding.inflate(layoutInflater)
}