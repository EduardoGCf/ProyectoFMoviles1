package com.example.proyectofinalcliente.data.network

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import com.example.proyectofinalcliente.ui.LoginActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitInstance {
    private var context: Context? = null

    fun initialize(appContext: Context) {
        context = appContext
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val sharedPreferences = context?.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                val token = sharedPreferences?.getString("auth_token", null)
                val request = chain.request().newBuilder()

                if (token != null) {
                    request.addHeader("Authorization", "Bearer $token")
                }

                val response = chain.proceed(request.build())

                if (response.code == 401) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context?.startActivity(intent)
                }

                response
            }
            .build()
    }

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://proyectodelivery.jmacboy.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
