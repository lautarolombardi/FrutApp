package com.example.frutapp

import FruitAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frutapp.data.api.ApiService
import com.example.frutapp.data.api.RetrofitClient
import com.example.frutapp.data.model.FruitDTO
import retrofit2.Call
import retrofit2.Response

class FruitList : AppCompatActivity(), FruitListFragment.OnUpdateClickListener {
  private lateinit var fruitAdapter: FruitAdapter
  private lateinit var progressBar: ProgressBar
  private val fruitList = mutableListOf<FruitDTO>()
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
    progressBar = findViewById(R.id.progressBar)
    recyclerView.layoutManager = LinearLayoutManager(this)

    fruitAdapter = FruitAdapter(mutableListOf())
    recyclerView.adapter = fruitAdapter

    loadFruits()
  }

  private fun loadFruits() {
    val api = RetrofitClient.retrofit.create(ApiService::class.java)
    val callGetAllFruits = api.getAllFruits()
    progressBar.visibility = View.VISIBLE
    callGetAllFruits.enqueue(object : retrofit2.Callback<List<FruitDTO>> {
      override fun onResponse(call: Call<List<FruitDTO>>, response: Response<List<FruitDTO>>) {
        progressBar.visibility = View.GONE
        if (response.isSuccessful) {
          val fruits = response.body() ?: emptyList()
          fruitList.clear()
          fruitList.addAll(fruits)
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

  override fun onUpdateClicked() {
    Toast.makeText(this, "Actualizando lista...", Toast.LENGTH_SHORT).show()
    loadFruits()
  }

  private fun ordenarListaFrutas() {
    fruitList.sortBy { it.name }        // ordeno la lista local
    fruitAdapter.updateData(fruitList)  // actualizo el adapter
    findViewById<RecyclerView>(R.id.recyclerViewFruits).scrollToPosition(0)
  }


  private fun logOut(){
    val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    sharedPrefs.edit().remove("username").apply()

    val intent = Intent(this, MainActivity::class.java).apply{
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
    finish()
  }


}