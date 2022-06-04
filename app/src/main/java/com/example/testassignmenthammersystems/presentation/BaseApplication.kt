package com.example.testassignmenthammersystems.presentation

import android.app.Application
import com.example.testassignmenthammersystems.data.database.FoodDatabase

class BaseApplication : Application() {
    val database: FoodDatabase by lazy { FoodDatabase.getDatabase(this) }
}