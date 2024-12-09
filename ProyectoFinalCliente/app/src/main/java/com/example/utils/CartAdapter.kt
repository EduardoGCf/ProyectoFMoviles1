package com.example.proyectofinalcliente.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalcliente.data.models.CartItem
import com.example.proyectofinalcliente.databinding.ItemCartBinding

class CartAdapter(
    private val items: List<CartItem>,
    private val updateTotalPrice: () -> Unit,
    private val removeProduct: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]

        with(holder.binding) {
            tvProductName.text = item.product.name
            tvProductPrice.text = "Precio: $${item.product.price.toDouble() * item.quantity}"
            tvProductQuantity.text = item.quantity.toString()

            Glide.with(root.context)
                .load(item.product.image)
                .into(ivProductImage)

            btnIncreaseQuantity.setOnClickListener {
                item.quantity++
                notifyItemChanged(position)
                updateTotalPrice()
            }

            btnDecreaseQuantity.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    notifyItemChanged(position)
                    updateTotalPrice()
                }
            }

            btnRemoveProduct.setOnClickListener {
                removeProduct(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
