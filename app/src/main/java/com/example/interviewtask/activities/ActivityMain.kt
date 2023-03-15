package com.example.interviewtask.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.interviewtask.databinding.ActivityStartGraphBinding

class ActivityMain: AppCompatActivity() {

    private var _binding: ActivityStartGraphBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStartGraphBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}