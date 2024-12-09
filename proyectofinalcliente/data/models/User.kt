package com.example.proyectofinalcliente.data.models

data class User(
    val id: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val role: Int
)
