package com.example.interviewtask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.interviewtask.adapter.ImagePagerAdapter
import com.example.interviewtask.databinding.ActivitySingleImageBinding
import com.example.interviewtask.models.ImageModel.ImageXX
import java.io.Serializable

class FragmentShowSingleImage : Fragment() {
    private var _binding: ActivitySingleImageBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivitySingleImageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList: Serializable? = activity?.intent?.getSerializableExtra("myData")

        val getIntentTitle = activity?.intent?.getStringExtra("title")

        val adapter = ImagePagerAdapter(requireContext(), dataList as List<ImageXX>)
        binding?.viewPager?.adapter = adapter
        binding?.titleImage?.text = "gdgijskjsdbl $getIntentTitle"
    }
}