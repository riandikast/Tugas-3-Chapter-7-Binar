package com.restoran.listmakanan.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    const val BASE_URL = "https://6284f061a48bd3c40b783f73.mockapi.io/"
    const val BASE_URL_Login = "https://627baa05a01c46a85322c5f7.mockapi.io/"

    private  val logging : HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val clint = OkHttpClient.Builder().addInterceptor(logging).build()

    val instance : ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clint)
            .build()
        retrofit.create(ApiService::class.java)
    }

    val instance2 : ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_Login)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clint)
            .build()
        retrofit.create(ApiService::class.java)
    }



}