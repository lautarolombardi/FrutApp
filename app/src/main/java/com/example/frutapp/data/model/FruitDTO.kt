package com.example.frutapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FruitDTO(
  val genus: String,
  val name: String,
  val id: Int,
  val family: String,
  val order: String,
  val nutritions: NutritionsDTO
)
