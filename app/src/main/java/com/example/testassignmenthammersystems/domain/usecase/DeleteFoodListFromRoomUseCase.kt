package com.example.testassignmenthammersystems.domain.usecase

import com.example.testassignmenthammersystems.domain.repository.FoodRepository

class DeleteFoodListFromRoomUseCase(private val foodRepository: FoodRepository) {

    suspend fun execute() {
        foodRepository.clearRoomDatabase()
    }
}