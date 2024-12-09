package com.example.proyectofinalcliente.ui


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalcliente.data.models.CartItem
import com.example.proyectofinalcliente.data.models.OrderDetail
import com.example.proyectofinalcliente.data.models.OrderRequest
import com.example.proyectofinalcliente.data.network.RetrofitInstance
import com.example.proyectofinalcliente.databinding.ActivityCartBinding
import com.example.proyectofinalcliente.utils.CartAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val cartItems = mutableListOf<CartItem>()
    private var totalPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedCartItems = intent.getParcelableArrayListExtra<CartItem>("cart_items")
        if (receivedCartItems != null) {
            cartItems.addAll(receivedCartItems)
        }

        val cartAdapter = CartAdapter(cartItems, ::updateTotalPrice, ::removeProduct)
        binding.rvCartItems.adapter = cartAdapter
        binding.rvCartItems.layoutManager = LinearLayoutManager(this)

        updateTotalPrice()

        binding.btnContinue.setOnClickListener {
            createOrder()
        }
    }

    private fun updateTotalPrice() {
        totalPrice = cartItems.sumOf { it.product.price.toDouble() * it.quantity }
        binding.tvTotalPrice.text = "Total a pagar: $$totalPrice"
    }

    private fun removeProduct(item: CartItem) {
        cartItems.remove(item)
        binding.rvCartItems.adapter?.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun createOrder() {
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío.", Toast.LENGTH_SHORT).show()
            return
        }

        val orderDetails = cartItems.map {
            OrderDetail(
                product_id = it.product.id,
                qty = it.quantity,
                price = (it.product.price.toDouble() * it.quantity).toString()
            )
        }

        val orderRequest = OrderRequest(
            restaurant_id = cartItems[0].product.restaurant_id,
            total = totalPrice.toString(),
            address = "Dirección ficticia",
            latitude = "0.0",
            longitude = "0.0",
            details = orderDetails
        )

        val api = RetrofitInstance.api
        api.createOrder(orderRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CartActivity, "Pedido realizado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@CartActivity, "Error al crear el pedido: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CartActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
