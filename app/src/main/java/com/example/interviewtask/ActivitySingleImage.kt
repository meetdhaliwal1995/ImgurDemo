package com.example.interviewtask

import android.R
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.interviewtask.adapter.ImagePagerAdapter
import com.example.interviewtask.databinding.SingleImageActivityBinding
import com.example.interviewtask.fragments.FragmentSingleImage
import com.example.interviewtask.models.ImageModel.ImageXX
import kotlinx.android.synthetic.main.single_image_activity.*


class ActivitySingleImage : AppCompatActivity(), java.io.Serializable {
    private var _binding: SingleImageActivityBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SingleImageActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        openFragment()
        val mylist = intent.getSerializableExtra("myData")
        val getIntentTitle = intent.getStringExtra("title")

//        val adapter = ImagePagerAdapter(this, mylist as List<ImageXX>)
//        binding?.viewPager?.adapter = adapter
//
//        binding?.titleImage?.text = getIntentTitle

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun openFragment() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment: FragmentSingleImage = FragmentSingleImage.newInstance()
        fragmentTransaction.replace(binding?.containerLayoutt?.id ?: 0, fragment)
        fragmentTransaction.commit()

    }

}

