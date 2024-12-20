package com.example.proyectofinalcliente.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalcliente.data.models.Restaurant
import com.example.proyectofinalcliente.databinding.ItemRestaurantBinding

class RestaurantAdapter(
    private val onRestaurantClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private var restaurants: List<Restaurant> = emptyList()

    fun submitList(newRestaurants: List<Restaurant>) {
        restaurants = newRestaurants
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size

    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            Glide.with(binding.imgR.context)
                .load(restaurant.logo)
                .into(binding.imgR)
            binding.tvRestaurantName.text = restaurant.name
            binding.root.setOnClickListener {
                onRestaurantClick(restaurant)
            }
        }
    }
}
