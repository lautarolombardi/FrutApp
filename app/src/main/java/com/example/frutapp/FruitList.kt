package com.example.frutapp

import FruitAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FruitList : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_fruit_list)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFruits)
    recyclerView.layoutManager = LinearLayoutManager(this)

    val frutas = getFrutas()

    recyclerView.adapter = FruitAdapter(frutas)
  }

  private fun getFrutas(): MutableList<Fruit> {
    val frutas: MutableList<Fruit> = ArrayList()

    frutas.add(
      Fruit(
        id = 1,
        name = "Banana",
        family = "Musaceae",
        genus = "Musa",
        order = "Zingiberales",
        nutritions = Nutritions(22.0, 1.1, 0.3, 96, 12.2)
      )
    )

    frutas.add(
      Fruit(
        id = 2,
        name = "Apple",
        family = "Rosaceae",
        genus = "Malus",
        order = "Rosales",
        nutritions = Nutritions(13.8, 0.3, 0.2, 52, 10.4)
      )
    )

    frutas.add(
      Fruit(
        id = 3,
        name = "Strawberry",
        family = "Rosaceae",
        genus = "Fragaria",
        order = "Rosales",
        nutritions = Nutritions(7.7, 0.8, 0.3, 33, 4.9)
      )
    )

    return frutas
  }

}