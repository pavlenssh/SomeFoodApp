package com.example.testassignmenthammersystems.domain.usecase

import com.example.testassignmenthammersystems.domain.models.Food
import com.example.testassignmenthammersystems.domain.repository.FoodRepository

class SaveFoodListToRoomUseCase(private val foodRepository: FoodRepository) {

    suspend fun execute(food: List<Food>) {
        for (element in food) {
            foodRepository.saveDataToRoom(element)
        }
    }
}