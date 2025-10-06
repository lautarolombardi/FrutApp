// Archivo: FruitList.kt

package com.example.frutapp

import FruitAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frutapp.data.api.ApiService
import com.example.frutapp.data.api.RetrofitClient
import com.example.frutapp.data.model.FruitDTO
import retrofit2.Call
import retrofit2.Response

// 1. La interfaz ahora está fuera de la clase, visible para todos.
interface OnFruitClickListener {
  fun onFruitClick(fruitData: Bundle)
}

// 2. La clase "promete" implementar el contrato de la interfaz (OnFruitClickListener)
class FruitList : AppCompatActivity(), OnFruitClickListener {
  private lateinit var fruitAdapter: FruitAdapter
  private lateinit var progressBar: ProgressBar
  private val fruitList = mutableListOf<FruitDTO>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fruit_list)
    setSupportActionBar(findViewById(R.id.toolbar))

    // --- Bloque onCreate corregido y ordenado ---
    // a. Primero, encuentra las Vistas en el layout.
    val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFruits)
    progressBar = findViewById(R.id.progressBar)

    // b. Prepara el RecyclerView.
    recyclerView.layoutManager = LinearLayoutManager(this)

    // c. Crea el Adapter, pasándole la lista y el "listener" (que es esta misma Activity, por eso 'this').
    fruitAdapter = FruitAdapter(fruitList, this)
    recyclerView.adapter = fruitAdapter

    // d. Lanza la petición a la API.
    obtenerFrutas()
  }

  // 3. Este es el método que cumple la promesa de la interfaz.
  // Aquí es donde ocurre la magia del cambio a Fragments.
  override fun onFruitClick(fruitData: Bundle) {
    val detailFragment = FruitDetailFragment()

    // Le pasamos los datos del clic al fragmento
    detailFragment.arguments = fruitData

    // Reemplazamos el contenido del contenedor por el nuevo fragmento
    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, detailFragment) // Busca el FrameLayout con este ID
      .addToBackStack(null) // Permite volver atrás
      .commit()
  }

  private fun obtenerFrutas() {
    val api = RetrofitClient.retrofit.create(ApiService::class.java)
    val callGetAllFruits = api.getAllFruits()
    progressBar.visibility = View.VISIBLE

    callGetAllFruits.enqueue(object : retrofit2.Callback<List<FruitDTO>> {
      override fun onResponse(call: Call<List<FruitDTO>>, response: Response<List<FruitDTO>>) {
        progressBar.visibility = View.GONE
        if (response.isSuccessful) {
          val fruits = response.body() ?: emptyList()
          fruitAdapter.updateData(fruits)
        }
      }

      override fun onFailure(call: Call<List<FruitDTO>>, t: Throwable) {
        progressBar.visibility = View.GONE
        t.printStackTrace()
      }
    })
  }

  @SuppressLint("RestrictedApi")
  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_fruit, menu)
    if (menu is MenuBuilder) {
      menu.setOptionalIconsVisible(true)
    }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_sort_name -> {
        ordenarListaFrutas()
        true
      }
      R.id.action_configuracion -> {
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
        true
      }
      R.id.action_logout -> {
        logOut()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun ordenarListaFrutas() {
    // Obtenemos una copia mutable para no modificar la original directamente aquí
    val sortedList = fruitList.toMutableList()
    sortedList.sortBy { it.name }
    fruitAdapter.updateData(sortedList)
    findViewById<RecyclerView>(R.id.recyclerViewFruits).scrollToPosition(0)
  }

  private fun logOut() {
    val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    sharedPrefs.edit().remove("username").apply()

    val intent = Intent(this, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
    finish()
  }
}
