package com.example.frutapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.frutapp.data.database.AppDatabase
import com.example.frutapp.utils.checkPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

  private lateinit var usernameEditText: EditText
  private lateinit var passwordEditText: EditText
  private lateinit var rememberMeCheckBox: CheckBox

  private val PREFS_NAME = "UserPrefs"
  private val PREF_USERNAME = "username"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    checkForRememberedUser()
    setContentView(R.layout.activity_main)

    usernameEditText = findViewById(R.id.etUserLogIn)
    passwordEditText = findViewById(R.id.etPasswordLogIn)
    rememberMeCheckBox = findViewById(R.id.cbRememberUser)

    val loginBtn = findViewById<Button>(R.id.btnLogIn)
    val registerTextView = findViewById<TextView>(R.id.tvDontHaveAccount)

    loginBtn.setOnClickListener {
      validarCredenciales()
    }

    registerTextView.setOnClickListener {
      startActivity(Intent(this, RegisterActivity::class.java))
    }
  }

  private fun checkForRememberedUser() {
    val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val rememberedUsername = sharedPrefs.getString(PREF_USERNAME, null)
    if (rememberedUsername != null) {
      navigateToFruitList(rememberedUsername)
    }
  }

  private fun validarCredenciales() {
    val username = usernameEditText.text.toString().trim()
    val password = passwordEditText.text.toString()

    if (username.isEmpty() || password.isEmpty()) {
      Toast.makeText(this, "User and password are required", Toast.LENGTH_SHORT).show()
      return
    }

    lifecycleScope.launch {
      try {
        val user = withContext(Dispatchers.IO) {
          AppDatabase.getDatabase(applicationContext).userDao().findByUsername(username)
        }

        if (user == null) {
          Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_SHORT).show()
          return@launch
        }

        val passwordMatches = withContext(Dispatchers.Default) {
          checkPassword(password, user.passwordHash)
        }

        if (passwordMatches) {
          handleRememberMe(username)
          navigateToFruitList(username)
        } else {
          Toast.makeText(this@MainActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
        }

      } catch (e: Exception) {
        Toast.makeText(this@MainActivity, "Error when trying to log in: ${e.message}", Toast.LENGTH_LONG).show()
      }
    }
  }

  private fun handleRememberMe(username: String) {
    val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    if (rememberMeCheckBox.isChecked) {
      sharedPrefs.edit().putString(PREF_USERNAME, username).apply()
    } else {
      sharedPrefs.edit().remove(PREF_USERNAME).apply()
    }
  }

  private fun navigateToFruitList(username: String) {
    Toast.makeText(this, "¡Welcome, $username!", Toast.LENGTH_SHORT).show()
    val intent = Intent(this, FruitList::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    finish()
  }
}

