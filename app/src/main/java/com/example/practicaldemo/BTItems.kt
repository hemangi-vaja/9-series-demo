package com.example.practicaldemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "btTable")
data class BTItems(
    @PrimaryKey @ColumnInfo var name: String,
    @ColumnInfo var mac: String
) {

}
