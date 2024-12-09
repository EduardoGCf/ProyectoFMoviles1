package com.example.proyectofinalcliente.data.models

import java.io.Serializable

data class OrderRequestDetail(
    val product_id: Int,
    val qty: Int,
    val price: Double
) : Serializable
