package com.example.frutapp.data.dao

// En UserDAO.kt

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.frutapp.data.entity.User

@Dao
interface UserDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(user: User)

  @Query("SELECT * FROM users WHERE username = :username")
  fun findByUsername(username: String): User?

  @Query("SELECT * FROM users WHERE email = :email")
  fun findByEmail(email: String): User?
}
