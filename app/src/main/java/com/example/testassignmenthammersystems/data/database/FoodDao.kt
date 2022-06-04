package com.example.testassignmenthammersystems.data.database

import androidx.room.*
import com.example.testassignmenthammersystems.data.models.FoodDataModel
import com.example.testassignmenthammersystems.domain.models.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_database ORDER BY id ASC")
    fun getFoods(): Flow<List<FoodDataModel>>

    @Query("SELECT * FROM food_database WHERE id = :id")
    fun getFood(id: Long): Flow<FoodDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodDataModel)

    @Update
    suspend fun updateFood(food: FoodDataModel)

    @Delete
    suspend fun deleteFood(food: FoodDataModel)

    @Query("DELETE FROM food_database")
    fun deleteAll()
}