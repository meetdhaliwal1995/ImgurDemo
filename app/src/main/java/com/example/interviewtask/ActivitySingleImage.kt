package com.example.interviewtask

import com.example.interviewtask.adapter.ImagePagerAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.interviewtask.databinding.ActivitySingleImageBinding
import com.example.interviewtask.models.ImageModel.ImageXX


class ActivitySingleImage : AppCompatActivity(), java.io.Serializable {
    private var _binding: ActivitySingleImageBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingleImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val mylist = intent.getSerializableExtra("myData")
        val getIntentTitle = intent.getStringExtra("title")

        val adapter = ImagePagerAdapter(this, mylist as List<ImageXX>)
        binding?.viewPager?.adapter = adapter

        binding?.titleImage?.text = getIntentTitle

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

