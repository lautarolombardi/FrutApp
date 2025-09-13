package com.example.frutapp.utils

import android.util.Patterns

// Validar email
fun isValidEmail(email: String): Boolean {
  return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

// Validar contraseña segura
fun isValidPassword(password: String): Boolean {
  val passwordPattern = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}$")
  return passwordPattern.matches(password)
}

// Validar nombre de usuario (opcional)
fun isValidUsername(username: String): Boolean {
  return username.length >= 8
}