package com.example.frutapp.data.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
  @GET("all")
  suspend fun getAllFruits(): Call<Fruit>

}