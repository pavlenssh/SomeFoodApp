package com.example.testassignmenthammersystems.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testassignmenthammersystems.R
import com.example.testassignmenthammersystems.databinding.BannerItemViewBinding

class BannerRecyclerViewAdapter : RecyclerView.Adapter<BannerRecyclerViewAdapter.BannerViewHolder>(){

    private val bannersList: List<Int> = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4,
        R.drawable.banner5,
    )

    class BannerViewHolder(
        val binding: BannerItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder {
        return BannerViewHolder(
            BannerItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: BannerViewHolder,
        position: Int
    ) {
        val item = bannersList[position]
        holder.binding.apply {
            bannerItemImageView.setImageResource(item)
        }
    }

    override fun getItemCount(): Int {
        return bannersList.size
    }
}