package com.example.proyectofinalcliente.data.models

data class Order(
    val id: Int,
    val user_id: Int,
    val restaurant_id: Int,
    val total: Double,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val status: Int,
    val created_at: String
)

