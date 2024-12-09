package com.example.proyectofinalcliente.data.models

data class OrderRequest(
    val restaurant_id: Int,
    val total: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val details: List<OrderDetail>
)