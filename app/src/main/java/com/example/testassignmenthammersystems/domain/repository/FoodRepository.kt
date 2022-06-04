package com.example.testassignmenthammersystems.domain.repository

import androidx.lifecycle.LiveData
import com.example.testassignmenthammersystems.domain.models.Food

interface FoodRepository {

    suspend fun getDataFromFirestore() : List<Food>

    fun getDataFromRoom() : LiveData<List<Food>>

    suspend fun saveDataToRoom(food: Food)

    suspend fun clearRoomDatabase()

}