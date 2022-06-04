package com.example.testassignmenthammersystems.data.firestore

import com.example.testassignmenthammersystems.data.models.FoodDataModel
import com.example.testassignmenthammersystems.domain.models.Food

interface Firestore {

    suspend fun getData() : List<FoodDataModel>

}