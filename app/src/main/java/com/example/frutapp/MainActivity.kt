package com.example.frutapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val loginBtn = findViewById<Button>(R.id.btnLogIn)
    loginBtn.setOnClickListener {
      startActivity(Intent(this, FruitList::class.java))
      finish()
    }

    val registerBtn = findViewById<TextView>(R.id.tvDontHaveAccount)
    registerBtn.setOnClickListener {
      startActivity(Intent(this, RegisterActivity::class.java))
    }
  }
}