package com.example.testassignmenthammersystems.presentation.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.testassignmenthammersystems.databinding.ActivityMainBinding
import com.example.testassignmenthammersystems.presentation.BaseApplication
import com.example.testassignmenthammersystems.presentation.adapters.BannerRecyclerViewAdapter
import com.example.testassignmenthammersystems.presentation.adapters.FoodListRecyclerViewAdapter
import com.example.testassignmenthammersystems.presentation.viewmodels.FoodViewModelFactory
import com.example.testassignmenthammersystems.presentation.viewmodels.MainViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private val myViewModel: MainViewModel by viewModels {
        FoodViewModelFactory(
            (application as BaseApplication).database.foodDao()
        )
    }

    private lateinit var binding: ActivityMainBinding

    private val foodListScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            var currentRecyclerViewPosition =
                (binding.foodListRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            if (currentRecyclerViewPosition < 0) currentRecyclerViewPosition =
                (binding.foodListRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            val firstCompletelyVisibleViewHolder =
                binding.foodListRecyclerView.findViewHolderForAdapterPosition(
                    currentRecyclerViewPosition
                ) as FoodListRecyclerViewAdapter.FoodViewHolder
            val typeOfViewHolder = firstCompletelyVisibleViewHolder.type
            val view: View =
                binding.typeChipGroup.children.first { getTypeFromChipText((it as Chip).text.toString()) == typeOfViewHolder }
            (view as Chip).isChecked = true
            scrollChipLine(view)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bannerAdapter = BannerRecyclerViewAdapter()
        val foodListAdapter = FoodListRecyclerViewAdapter()
        binding.apply {
            //lifecycleOwner = this@MainActivity
            bannerRecyclerView.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            bannerRecyclerView.adapter = bannerAdapter

            foodListRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            foodListRecyclerView.adapter = foodListAdapter
            foodListRecyclerView.addOnScrollListener(foodListScrollListener)
            typeChipGroup.forEach {
                it.setOnClickListener { scrollRecyclerViewToCertainItem() }
            }
        }

        myViewModel.localFoods.observe(this) { foods ->
            foodListAdapter.setData(foods)
        }

    }

    private fun scrollChipLine(chip: View) {
        val widthScreen = resources.displayMetrics.widthPixels / 2
        val chipCenter = (chip.right - chip.left) / 2
        try {
            binding.horizontalScrollView.smoothScrollTo(chip.left + chipCenter - widthScreen, 0)
        } catch (e: Exception) {
            Log.d("MainActivity", "Scroll Chip Line Error")
        }
    }

    private fun scrollRecyclerViewToCertainItem() {
        val chip = findViewById<Chip>(binding.typeChipGroup.checkedChipId)
        val chipText = chip?.text.toString()

        try {
            if (chipText != "null") {
                val smoothScroller = getSnapToStartLinearSmoothScroller()
                smoothScroller.targetPosition =
                    findFirstCertainFoodItem(getTypeFromChipText(chipText))
                val smoothScrollerPosition = smoothScroller.targetPosition
                binding.foodListRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)

                if (smoothScroller.targetPosition == 0)
                    binding.appBarLayout.setExpanded(true)
                else
                    binding.appBarLayout.setExpanded(false)
                binding.foodListRecyclerView.removeOnScrollListener(foodListScrollListener)
                scrollChipLine(chip)
                CoroutineScope(Dispatchers.IO).launch {
                    while (true) {
                        var currentRecyclerViewPosition =
                            (binding.foodListRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        if (currentRecyclerViewPosition < 0) currentRecyclerViewPosition =
                            (binding.foodListRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (currentRecyclerViewPosition == smoothScrollerPosition) {
                            binding.foodListRecyclerView.addOnScrollListener(foodListScrollListener)
                            break
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("MainActivity", "Scroll Recycler View Error")
        }
    }

    private fun findFirstCertainFoodItem(type: String): Int {
        val item = myViewModel.food.value?.first { it.type == type }
        return myViewModel.food.value!!.indexOf(item)
    }

    private fun getTypeFromChipText(text: String): String {
        return when (text) {
            "Пицца" -> "pizza"
            "Комбо" -> "combo"
            "Закуски" -> "snack"
            "Десерты" -> "desert"
            "Напитки" -> "drink"
            "Соусы" -> "souce"
            else -> "other"
        }
    }

    private fun getSnapToStartLinearSmoothScroller(): LinearSmoothScroller {
        return object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
    }
}