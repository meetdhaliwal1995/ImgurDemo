package com.example.interviewtask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.interviewtask.databinding.ActivityMainBinding
import com.example.interviewtask.databinding.ActivitySingleImageBinding

class ActivitySingleImage : AppCompatActivity() {
    private var _binding: ActivitySingleImageBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingleImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val getIntentLink = intent.getStringExtra("link")
        val getIntentViews = intent.getStringExtra("views")
        val getIntentTitle = intent.getStringExtra("title")
        val getIntentDescription = intent.getStringExtra("description")

        binding?.ivImage?.let {
            Glide.with(this)
                .load(getIntentLink)
                .error(android.R.drawable.ic_menu_report_image)
                .into(it)
        }

        binding?.titleImage?.text = getIntentTitle
        binding?.tvViews?.text = getIntentViews
        binding?.tvDescription?.text = getIntentDescription

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}