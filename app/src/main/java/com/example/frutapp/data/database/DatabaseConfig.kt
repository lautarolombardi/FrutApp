package com.example.frutapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.frutapp.data.dao.UserDao
import com.example.frutapp.data.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
  abstract fun userDao(): UserDao
  companion object{
    private var INSTANCIA: AppDatabase? = null
    fun getDatabase(context: Context): AppDatabase {
      if(INSTANCIA == null){
        synchronized(this){
          INSTANCIA = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "frutapp_db")
            .fallbackToDestructiveMigration(false)
            .build()
        }
      }
      return INSTANCIA!!
    }
  }
}
