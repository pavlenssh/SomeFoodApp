package com.example.testassignmenthammersystems.domain.usecase

import androidx.lifecycle.LiveData
import com.example.testassignmenthammersystems.domain.models.Food
import com.example.testassignmenthammersystems.domain.repository.FoodRepository

class GetFoodListFromRoomUseCase(private val foodRepository: FoodRepository) {

    fun execute(): LiveData<List<Food>> {
        return foodRepository.getDataFromRoom()
    }
}