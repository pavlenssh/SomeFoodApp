package com.example.testassignmenthammersystems.presentation.viewmodels

import androidx.lifecycle.*
import com.example.testassignmenthammersystems.data.database.FoodDao
import com.example.testassignmenthammersystems.data.repository.FoodRepositoryImpl
import com.example.testassignmenthammersystems.domain.models.Food
import com.example.testassignmenthammersystems.domain.usecase.DeleteFoodListFromRoomUseCase
import com.example.testassignmenthammersystems.domain.usecase.GetFoodListFromFirestoreUseCase
import com.example.testassignmenthammersystems.domain.usecase.GetFoodListFromRoomUseCase
import com.example.testassignmenthammersystems.domain.usecase.SaveFoodListToRoomUseCase
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class MainViewModel(
    private val foodDao: FoodDao
) : ViewModel() {

    private val foodRepository = FoodRepositoryImpl(foodDao)

    private val getFoodListFromFirestoreUseCase = GetFoodListFromFirestoreUseCase(foodRepository)
    private val getFoodListFromRoomUseCase = GetFoodListFromRoomUseCase(foodRepository)
    private val saveFoodListToRoomUseCase = SaveFoodListToRoomUseCase(foodRepository)
    private val deleteFoodListFromRoomUseCase = DeleteFoodListFromRoomUseCase(foodRepository)

    val food  =  MutableLiveData<List<Food>>()
    val localFoods : LiveData<List<Food>> = getFoodListFromRoomUseCase.execute()

    init {
        viewModelScope.launch {
            food.value = getFoodListFromFirestoreUseCase.execute()
        }
        food.observeForever {
            if (food.value != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    deleteFoodListFromRoomUseCase.execute()
                    saveFoodListToRoomUseCase.execute(food.value!!)
                }
            }
        }
    }

    private fun deleteFromDatabase() {
        GlobalScope.launch(Dispatchers.IO) {
            deleteFoodListFromRoomUseCase.execute()
        }
    }

    private fun insertToDataBase() {
        GlobalScope.launch(Dispatchers.IO) {

        }
    }
}

class FoodViewModelFactory(
    private val foodDao: FoodDao
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(foodDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}