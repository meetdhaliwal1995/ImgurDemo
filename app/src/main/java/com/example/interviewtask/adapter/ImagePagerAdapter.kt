package com.example.interviewtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.interviewtask.databinding.ImageContainerBinding
import com.example.interviewtask.models.ImageModel.ImageXX

class ImagePagerAdapter(private val context: Context, private val images: List<ImageXX>) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ImageContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageRes = images[position]
        imageRes.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(private val binding: ImageContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imzData: ImageXX) {

            Glide.with(context)
                .load(imzData.link)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.imageContainer)

            binding.tvDescription.text = imzData.description
            binding.tvViews.text = imzData.views.toString()

        }

    }
}

data class ImageData(val imageResId: Int)

