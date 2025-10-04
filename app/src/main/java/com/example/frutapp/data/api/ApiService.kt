package com.example.frutapp.data.api

import com.example.frutapp.data.model.FruitDTO
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
  @GET("all")
  fun getAllFruits(): Call<List<FruitDTO>>

}