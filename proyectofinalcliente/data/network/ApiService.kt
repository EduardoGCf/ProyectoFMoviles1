package com.example.proyectofinalcliente.data.network

import com.example.proyectofinalcliente.data.models.AuthResponse
import com.example.proyectofinalcliente.data.models.LoginRequest
import com.example.proyectofinalcliente.data.models.Order
import com.example.proyectofinalcliente.data.models.OrderRequest
import com.example.proyectofinalcliente.data.models.Product
import com.example.proyectofinalcliente.data.models.Restaurant
import com.example.proyectofinalcliente.data.models.RestaurantWithMenuResponse
import com.example.proyectofinalcliente.data.models.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @POST("users/login")
    fun login(@Body loginRequest: LoginRequest): Call<AuthResponse>

    @POST("users")
    fun register(@Body user: User): Call<User>

    @GET("restaurants")
    fun getRestaurants(): Call<List<Restaurant>>

    @GET("orders")
    fun getOrders(): Call<List<Order>>

    @GET("restaurants/{restaurant_id}")
    fun getRestaurantMenu(
        @Path("restaurant_id") restaurantId: Int
    ): Call<RestaurantWithMenuResponse>


    @POST("orders")
    fun createOrder(@Body orderRequest: OrderRequest): Call<Void>

}


