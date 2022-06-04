package com.example.testassignmenthammersystems.data.firestore

import com.example.testassignmenthammersystems.data.models.FoodDataModel
import com.example.testassignmenthammersystems.domain.models.Food
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FoodFirestoreImpl : Firestore {

    private val fireStore = FirebaseFirestore.getInstance()

    override suspend fun getData(): List<FoodDataModel> {
        val list = mutableListOf<FoodDataModel>()
        coroutineScope {
            val snapshot = fireStore.collection("food").get().await()
            for (element in snapshot.documents) {
                element.toObject(FoodDataModel::class.java)?.let { list.add(it) }
            }
        }
        return list
    }
}