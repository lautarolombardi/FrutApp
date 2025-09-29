package com.example.frutapp

import FruitAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FruitList : AppCompatActivity() {
  private lateinit var fruitAdapter: FruitAdapter
  private lateinit var frutas: MutableList<Fruit>
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_fruit_list)
    setSupportActionBar(findViewById(R.id.toolbar))

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFruits)
    recyclerView.layoutManager = LinearLayoutManager(this)

    frutas = getFrutas()

    fruitAdapter = FruitAdapter(frutas)
    recyclerView.adapter = fruitAdapter
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


  @SuppressLint("RestrictedApi")
  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_fruit, menu)
    if(menu is MenuBuilder){
      menu.setOptionalIconsVisible(true)
    }
    return true;
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean{
    return when(item.itemId){

      R.id.action_sort_name->{
        ordenarListaFrutas()
        true
      }

      R.id.action_configuracion->{
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
        true
      }

      R.id.action_logout->{
        logOut()
        true
      }

      else->super.onOptionsItemSelected(item)

    }
  }

  private fun ordenarListaFrutas(){
    frutas.sortBy { it.name }
    fruitAdapter.notifyDataSetChanged()

    findViewById<RecyclerView>(R.id.recyclerViewFruits).scrollToPosition(0)
  }

  private fun logOut(){
    val intent = Intent(this, MainActivity::class.java).apply{
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
    finish()
  }



}