package com.example.frutapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.frutapp.data.entity.User

@Dao
interface UserDao {
  @Insert
  fun insert(user: User)

  @Query("SELECT * FROM users WHERE email = :email")
  fun findByEmail(email: String): User?
}
