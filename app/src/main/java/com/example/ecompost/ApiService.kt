package com.example.ecompost

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("register_proveedor/")
    fun registerProveedor(@Body user: User): Call<TokenResponse>

    @POST("login/")
    fun loginProveedor(@Body user: User): Call<TokenResponse>

    @GET("profile_proveedor/")
    fun getProfile(@Header("Authorization") token: String): Call<ProfileResponse>
}