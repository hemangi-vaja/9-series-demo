package com.example.practicaldemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practicaldemo.BTItems

@Database(entities = [BTItems::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun btDao(): BTDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}