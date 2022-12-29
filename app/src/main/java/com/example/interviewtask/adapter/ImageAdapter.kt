package com.example.interviewtask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.interviewtask.models.ImageModel.Data
import com.example.interviewtask.databinding.ImageItemBinding
import java.text.SimpleDateFormat
import java.util.*

//ComparatorDiffUtil is a utility class that is used to compare two lists of items and calculate the differences between them.
// it is typically used in conjunction with the Android DiffUtil class to calculate the differences between two lists of items and display them in a RecyclerView.


class ImageAdapter(val context: Context, private val onNoteClicked: (Data) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Data, ImageAdapter.NoteViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val image = getItem(position)
        image?.let {
            holder.bind(it)
        }
    }

    inner class NoteViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imzData: Data) {
            binding.textDate.text = convertDateTime(imzData.datetime)
            binding.titleText.text = imzData.title
            if (imzData.images?.isNotEmpty() == true) {
                binding.imageCount.visibility = View.VISIBLE
                binding.imageCount.text = imzData.images.size.toString()
            }

            Glide.with(context)
                .load(imzData.images?.get(0)?.link ?: "")
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                onNoteClicked(imzData)
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertDateTime(date: Int): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = (date * 1000L)
        val dateFormat = SimpleDateFormat("h:mm a  dd MMM, yy ")
        return dateFormat.format(calendar.time)
    }
}
