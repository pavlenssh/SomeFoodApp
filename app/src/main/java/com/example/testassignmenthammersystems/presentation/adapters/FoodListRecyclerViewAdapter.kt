package com.example.testassignmenthammersystems.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testassignmenthammersystems.R
import com.example.testassignmenthammersystems.databinding.FoodListItemViewBinding
import com.example.testassignmenthammersystems.domain.models.Food

class FoodListRecyclerViewAdapter :
    RecyclerView.Adapter<FoodListRecyclerViewAdapter.FoodViewHolder>() {

    private var foodList = emptyList<Food>()

    class FoodViewHolder(
        val binding: FoodListItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        lateinit var type: String
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodViewHolder {
        return FoodViewHolder(
            FoodListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: FoodViewHolder,
        position: Int
    ) {
        val item = foodList[position]
        holder.binding.apply {
            titleTextView.text = item.name
            descriptionTextView.text = item.description
            chipButton.text = item.price.toString()
            imageView.load(item.imageUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_connection_error)
            }
        }
        holder.type = item.type
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(newList: List<Food>) {
        val diffUtil = DiffCallback(foodList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        foodList = newList
        diffResults.dispatchUpdatesTo(this)
    }
}

class DiffCallback(
    private val oldList: List<Food>,
    private val newList: List<Food>
) : DiffUtil.Callback(){


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id ->
                false
            oldList[oldItemPosition].name == newList[newItemPosition].name ->
                false
            oldList[oldItemPosition].description == newList[newItemPosition].description ->
                false
            oldList[oldItemPosition].price == newList[newItemPosition].price ->
                false
            else -> true
        }
    }

}