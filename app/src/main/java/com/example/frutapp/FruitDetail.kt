package com.example.frutapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class FruitDetail : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_fruit_detail)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val bundle: Bundle? = intent.extras
    if (bundle != null) {
      val intentNameValue = bundle?.getString("name")
      val intentEmoji = bundle?.getString("emoji")

      val intentFamilyValue = bundle?.getString("family")
      val intentGenusValue = bundle?.getString("genus")
      val intentOrderValue = bundle?.getString("order")

      val intentCarbohydratesValue = bundle?.getDouble("carbohydrates")
      val intentProteinValue = bundle?.getDouble("protein")
      val intentFatValue = bundle?.getDouble("fat")
      val intentCaloriesValue = bundle?.getInt("calories")
      val intentSugarValue = bundle?.getDouble("sugar")


      val emoji: TextView = findViewById(R.id.tvEmoji)
      emoji.text = intentEmoji.toString()

      val name: TextView = findViewById(R.id.tvNameValue)
      name.text = intentNameValue.toString()

      val family: TextView = findViewById(R.id.tvFamilyValue)
      family.text = intentFamilyValue.toString()

      val genus: TextView = findViewById(R.id.tvGenusValue)
      genus.text = intentGenusValue.toString()

      val order: TextView = findViewById(R.id.tvOrdenValue)
      order.text = intentOrderValue.toString()

      val carbohydrates: TextView = findViewById(R.id.tvCarbohydratesValue)
      carbohydrates.text = intentCarbohydratesValue.toString()

      val protein: TextView = findViewById(R.id.tvProteinValue)
      protein.text = intentProteinValue.toString()

      val fat: TextView = findViewById(R.id.tvFatValue)
      fat.text = intentFatValue.toString()

      val calories: TextView = findViewById(R.id.tvCaloriesValue)
      calories.text = intentCaloriesValue.toString()

      val sugar: TextView = findViewById(R.id.tvSugarValue)
      sugar.text = intentSugarValue.toString()
    }

  }
}