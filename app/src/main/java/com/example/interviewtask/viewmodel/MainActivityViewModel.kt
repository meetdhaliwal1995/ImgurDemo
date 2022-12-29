package com.example.interviewtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewtask.helper.ResultWrapper
import com.example.interviewtask.models.ImageModel.Data
import com.example.interviewtask.repository.ImageRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {

    private val _imageData: MutableLiveData<List<Data?>?> = MutableLiveData()
    val imageData: MutableLiveData<List<Data?>?> = _imageData

    private val _searchImage: MutableLiveData<List<Data?>?> = MutableLiveData()
    val searchImage: LiveData<List<Data?>?> = _searchImage

    private val _errorString: MutableLiveData<String> = MutableLiveData()
    val errorString: LiveData<String> = _errorString

    fun getViralImages(page: Int = 0) {
        viewModelScope.launch {
            imageRepository.getViralPost(page).collectLatest {
                when (it) {
                    is ResultWrapper.GenericError -> {
                        val err = it.error?.message ?: "Error"

                        _errorString.postValue(err)
                    }
                    is ResultWrapper.Success -> {
                        val listData = it.value.data

                        val result = sortListByTimeDescending(listData)

                        _imageData.postValue(result)
                    }
                }
            }
        }
    }

    fun getSearchedImages(q: String) {
        viewModelScope.launch {
            imageRepository.getSearchedData(q).collectLatest {
                when (it) {
                    is ResultWrapper.GenericError -> {
                        val err = it.error?.message ?: "Error"

                        _errorString.postValue(err)
                    }
                    is ResultWrapper.Success -> {
                        val listData = it.value.data

                        val result = sortListByTimeDescending(listData)

                        _searchImage.postValue(result)
                    }
                }
            }
        }
    }

    private fun sortListByTimeDescending(listData: List<Data>?): List<Data>? {
        return listData?.sortedByDescending { data ->
            data.datetime
        }
    }
}
