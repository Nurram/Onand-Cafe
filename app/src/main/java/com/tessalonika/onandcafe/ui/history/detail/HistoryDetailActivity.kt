package com.tessalonika.onandcafe.ui.history.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.visible
import com.tessalonika.onandcafe.databinding.ActivityHistoryDetailBinding
import com.tessalonika.onandcafe.model.OrderWithMenu
import com.tessalonika.onandcafe.ui.ViewModelFactory
import com.tessalonika.onandcafe.ui.history.HistoryAdapter
import com.tessalonika.onandcafe.ui.history.HistoryViewModel
import com.tessalonika.onandcafe.ui.order.OrderAdapter

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDetailBinding
    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbHistoryDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val orderId = intent.getLongExtra("orderId", -1)
        val factory = ViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        if (orderId > -1) {
            viewModel.getOrderByOrderId(orderId).observe(this) {
                if (it != null) initUI(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun initUI(orderWithMenu: OrderWithMenu) {
        val order = orderWithMenu.order
        val menus = orderWithMenu.menus

        val adapter = HistoryDetailAdapter(this)
        adapter.setData(menus)

        binding.apply {
            tilName.editText?.setText(order.buyerName)
            tilTableNo.editText?.setText(order.tableNo)
            tilCash.editText?.setText(order.totalPrice.toString())

            rvMenus.adapter = adapter
            rvMenus.layoutManager = LinearLayoutManager(this@HistoryDetailActivity)

            btnDone.setOnClickListener {
                viewModel.setPaymentStatus(1, order.orderId.toString())
                finish()
            }

            if (order.isPaid == 1) {
                btnDone.visible(false)
            }
        }
    }


}