package com.example.frutapp

import com.example.frutapp.Nutritions

data class Fruit(
  val name: String,
  val id: Int,
  val family: String,
  val order: String,
  val genus: String,
  val emoji: String = when (name) {
    "Banana" -> "🍌"
    "Apple" -> "🍎"
    "Strawberry" -> "🍓"
    else -> ""
  },
  val nutritions: Nutritions,
)