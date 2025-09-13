package com.example.frutapp.utils

import org.mindrot.jbcrypt.BCrypt

fun hashPassword(password: String): String {
  return BCrypt.hashpw(password, BCrypt.gensalt())
}

fun checkPassword(plain: String, hashed: String): Boolean {
  return BCrypt.checkpw(plain, hashed)
}