package com.example.frutapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
  val username: String,
  val email: String,
  val password: String
) {
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0
}
