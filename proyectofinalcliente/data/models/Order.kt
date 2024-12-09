package com.example.deliveryapp.data.model

data class Order(
    val id: Int,
    val total: Double,
    val address: String,
    val status: Int,
    val createdAt: String
)
