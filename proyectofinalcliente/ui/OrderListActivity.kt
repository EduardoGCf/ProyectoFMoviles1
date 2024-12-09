package com.example.proyectofinalcliente.ui
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.ui.adapters.OrderAdapter
import com.example.proyectofinalcliente.data.network.RetrofitInstance
import com.example.proyectofinalcliente.databinding.ActivityOrderListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderListBinding
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchOrders()
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter()
        binding.rvOrderList.apply {
            layoutManager = LinearLayoutManager(this@OrderListActivity)
            adapter = orderAdapter
        }
    }

    private fun fetchOrders() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getOrders()
                if (response.isSuccessful && response.body() != null) {
                    val orders = response.body()!!
                    runOnUiThread {
                        orderAdapter.submitList(orders)
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@OrderListActivity, "Error al cargar pedidos", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@OrderListActivity, "Error en la conexión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
