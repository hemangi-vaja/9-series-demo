package com.example.practicaldemo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicaldemo.BTItems

@Dao
interface BTDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBT(btItems: BTItems)

    @Query("SELECT * FROM btTable")
    fun getBTList():  List<BTItems>

}