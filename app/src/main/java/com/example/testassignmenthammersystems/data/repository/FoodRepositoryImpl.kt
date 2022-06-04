package com.example.testassignmenthammersystems.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.example.testassignmenthammersystems.data.database.FoodDao
import com.example.testassignmenthammersystems.data.firestore.FoodFirestoreImpl
import com.example.testassignmenthammersystems.data.models.FoodDataModel
import com.example.testassignmenthammersystems.domain.models.Food
import com.example.testassignmenthammersystems.domain.repository.FoodRepository


class FoodRepositoryImpl(private val foodDao: FoodDao) : FoodRepository {

    private val foodFireStore = FoodFirestoreImpl()

    override suspend fun getDataFromFirestore() : List<Food> {
        return foodFireStore.getData().toFoodList()
    }

    override fun getDataFromRoom(): LiveData<List<Food>> {
        return foodDao.getFoods().asLiveData().toLiveDataListFood()
    }

    override suspend fun saveDataToRoom(food: Food) {
        foodDao.insertFood(food.toFoodDataModel())
    }

    override suspend fun clearRoomDatabase() {
        foodDao.deleteAll()
    }

}

private fun List<FoodDataModel>.toFoodList() : List<Food> {
    val foodList = mutableListOf<Food>()
    for (element in this) {
        foodList.add(Food(
            element.id,
            element.name,
            element.description,
            element.price,
            element.type,
            element.imageUrl
        ))
    }

    return foodList
}

private fun LiveData<List<FoodDataModel>>.toLiveDataListFood() : LiveData<List<Food>> {
    return Transformations.map(this) { list -> list.map { Food(it.id, it.name, it.description, it.price, it.type, it.imageUrl) }}
}

private fun Food.toFoodDataModel(): FoodDataModel {
    return FoodDataModel(
        this.id,
        this.name,
        this.description,
        this.price,
        this.type,
        this.imageUrl
    )
}



