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
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

  private lateinit var usernameEditText: EditText
  private lateinit var passwordEditText: EditText
  private lateinit var rememberMeCheckBox: CheckBox

  private val PREFS_NAME = "UserPrefs"
  private val PREF_USERNAME = "username"
  private val CHANNEL_ID = "REMINDER_CHANNEL"
  private val NOTIFICATION_ID = 101

  private val requestPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
      if (isGranted) {
        sendPersistentNotification(
          "CREDENTIALS SAVED",
          "The system will now remember your username and password for automatic login."
        )
      } else {
        Toast.makeText(this, "Notification permission denied.", Toast.LENGTH_SHORT).show()
      }
    }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    checkForRememberedUser()
    createNotificationChannel()
    setContentView(R.layout.activity_main)

    usernameEditText = findViewById(R.id.etUserLogIn)
    passwordEditText = findViewById(R.id.etPasswordLogIn)
    rememberMeCheckBox = findViewById(R.id.cbRememberUser)

    rememberMeCheckBox.setOnCheckedChangeListener { _, isChecked ->
      if(isChecked){
        checkAndSendNotification(
          "CREDENTIALS SAVED",
          "The system will now remember your username and password for automatic login."
        )
      }else{
        Toast.makeText(this, "Notification: Credentials will NOT be saved.", Toast.LENGTH_SHORT).show()
      }
    }

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

  private fun checkAndSendNotification(title: String, content: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if(ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
        sendPersistentNotification(title, content)
      }else{
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
      }
    } else{
      sendPersistentNotification(title, content)
    }
  }

  private fun createNotificationChannel(){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
      val name = "Session Reminders"
      val descriptionText = "Notifications related to saved login credentials."
      val importance = NotificationManager.IMPORTANCE_DEFAULT

      val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
      }

      val notificationManager: NotificationManager =
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }

  @SuppressLint("MissingPermission")
  private fun sendPersistentNotification(title: String, content: String){
    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_remember_me)
      .setContentTitle(title)
      .setContentText(content)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setAutoCancel(true)
      .setStyle(NotificationCompat.BigTextStyle().bigText(content))

    with(NotificationManagerCompat.from(this)){
      notify(NOTIFICATION_ID, builder.build())
    }
  }

}