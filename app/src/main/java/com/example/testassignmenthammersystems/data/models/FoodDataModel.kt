package com.example.testassignmenthammersystems.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "food_database")
data class FoodDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "price")
    val price: Int = 0,
    @ColumnInfo(name = "type")
    val type: String = "",
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String = ""
) {
    fun getFormattedPrice(): String = NumberFormat.getCurrencyInstance().format(price)
}