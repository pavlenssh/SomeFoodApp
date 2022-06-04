package com.example.testassignmenthammersystems.domain.usecase

import com.example.testassignmenthammersystems.domain.models.Food
import com.example.testassignmenthammersystems.domain.repository.FoodRepository

class GetFoodListFromFirestoreUseCase(private val foodRepository: FoodRepository) {

    suspend fun execute(): List<Food> {
        return foodRepository.getDataFromFirestore()
    }
}