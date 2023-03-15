package com.example.interviewtask.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.interviewtask.R
import com.example.interviewtask.adapter.ImagePagerAdapter
import com.example.interviewtask.databinding.ActivitySingleImageBinding
import com.example.interviewtask.models.ImageModel.ImageXX
import java.io.Serializable


class FragmentSingleImage : Fragment() {
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
        binding?.titleImage?.text = getIntentTitle
        Log.e("Clickkkkk", "dlmmsbk;bd")

        binding?.titleImage?.setOnClickListener {
            Log.e("Clickkkkk", "ddddd")
            replaceFragment()
        }
    }


    private fun replaceFragment() {
        val fragment = FragmentShowSingleImage()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container_layoutt, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    companion object {
        fun newInstance(): FragmentSingleImage {
            val fragment = FragmentSingleImage()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}


