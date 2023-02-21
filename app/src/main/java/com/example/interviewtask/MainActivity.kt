package com.example.interviewtask

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.interviewtask.adapter.ImageAdapter
import com.example.interviewtask.databinding.ActivityMainBinding
import com.example.interviewtask.viewmodel.MainActivityViewModel
import com.example.interviewtask.viewmodel.MainViewModelFactory
import java.util.ArrayList
import javax.inject.Inject


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var adapter: ImageAdapter
    private var count = 3

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        (application as ImageApplication).applicationComponent.inject(this)

        mainActivityViewModel =
            ViewModelProvider(this, mainViewModelFactory)[MainActivityViewModel::class.java]

        bindObservers()


        adapter = ImageAdapter(this) {
            val intent = Intent(this, ActivitySingleImage::class.java)
            intent.putExtra("title", it.title)
            intent.putExtra("myData", it.images as ArrayList)
            startActivity(intent)
        }

        binding?.recyclerView?.layoutManager = GridLayoutManager(this, count)
        binding?.recyclerView?.adapter = adapter

        mainActivityViewModel.getViralImages(0)

        binding?.searchButton?.setOnClickListener(this)
        binding?.tButton?.setOnClickListener(this)
        binding?.searchBtn?.setOnClickListener(this)
        binding?.ivUploadBtn?.setOnClickListener(this)

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            mainActivityViewModel.getViralImages()
            binding?.swipeRefreshLayout?.isRefreshing = false
        }

        manageProgressBar(true)
    }

    private fun bindObservers() {
        mainActivityViewModel.imageData.observe(this) {
            manageProgressBar(false)
            if (it?.size == 0) {
                manageErrorHandling(true)
                binding?.errorIv?.setImageResource(R.drawable.error);
                adapter.submitList(it)
            } else {
                manageErrorHandling(false)
                adapter.submitList(it)
            }
        }

        mainActivityViewModel.searchImage.observe(this) {
            manageProgressBar(false)
            if (it?.size == 0) {
                manageErrorHandling(true)
                binding?.errorIv?.setImageResource(R.drawable.error);
                adapter.submitList(it)
            } else {
                manageErrorHandling(false)
                adapter.submitList(it)
            }
        }

        mainActivityViewModel.errorString.observe(this) {
            manageProgressBar(false)
            binding?.errorIv?.setImageResource(R.drawable.auth_error);

        }

        binding?.searchEditText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submitSearch()
            }
            false
        }
    }

    private fun manageProgressBar(show: Boolean) {
        if (show) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun manageErrorHandling(show: Boolean) {
        if (show) {
            binding?.errorIv?.visibility = View.VISIBLE
        } else {
            binding?.errorIv?.visibility = View.GONE
        }
    }

    private fun manageSearchBar(show: Boolean) {
        if (show) {
            binding?.searchBar?.visibility = View.VISIBLE
        } else {
            binding?.searchBar?.visibility = View.GONE
        }
    }

    private fun listGridToggle(count: Int) {
        this.count = count
        binding?.recyclerView?.layoutManager = GridLayoutManager(this, count)
    }

    private fun submitSearch() {
        val searchStr = binding?.searchEditText?.text.toString()

        if (searchStr != "") {
            mainActivityViewModel.getSearchedImages(searchStr)
            manageProgressBar(true)
        }

        binding?.searchButton?.let { hideKeyboard(it) }
        manageSearchBar(false)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.searchButton -> {
                manageSearchBar(true)
                binding?.searchEditText?.text = null
                showKeyboard(v)
            }
            R.id.tButton -> {
                if (count == 3) {
                    listGridToggle(1)
                } else {
                    listGridToggle(3)
                }
            }
            R.id.searchBtn -> {
                submitSearch()
            }
            R.id.ivUploadBtn -> {
                val intent = Intent(this, ActivityUploadImage::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}