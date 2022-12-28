package com.example.interviewtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask.repository.ImageRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val repo: ImageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repo) as T
    }
}