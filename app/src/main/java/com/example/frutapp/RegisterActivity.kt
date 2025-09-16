package com.example.frutapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.frutapp.data.database.AppDatabase
import com.example.frutapp.data.entity.User
import com.example.frutapp.utils.hashPassword
import com.example.frutapp.utils.isValidEmail
import com.example.frutapp.utils.isValidPassword
import com.example.frutapp.utils.isValidUsername
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            val username = findViewById<TextView>(R.id.etUserRegister).text.toString()
            val email = findViewById<TextView>(R.id.etMailRegister).text.toString()
            val password = findViewById<TextView>(R.id.etPasswordRegister).text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidUsername(username)){
                Toast.makeText(this, "Enter a username with at least 8 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPassword(password)) {
                Toast.makeText(this, "Password must contain at least 8 characters, one uppercase letter, one number and one special character", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!findViewById<CheckBox>(R.id.cbTermsConditions).isChecked) {
                Toast.makeText(this, "You must accept the Terms & Conditions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val existingUser = withContext(Dispatchers.IO) {
                    AppDatabase.getDatabase(applicationContext).userDao().findByEmail(email)
                }

                if (existingUser != null) {
                    Toast.makeText(this@RegisterActivity, "User already exists", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val hashedPass = hashPassword(password)

                withContext(Dispatchers.IO) {
                    AppDatabase.getDatabase(applicationContext).userDao().insert(User(username, email, hashedPass))
                }

                Toast.makeText(this@RegisterActivity, "User successfully registered", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            }
        }

        val tvReturnLogin = findViewById<TextView>(R.id.tvHaveAnAccount)
        tvReturnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}