package com.example.interviewtask.repository

import com.example.interviewtask.helper.NetworkHelper
import com.example.interviewtask.helper.ResultWrapper
import com.example.interviewtask.models.ImageModel.ViralImage
import com.example.interviewtask.R
import com.example.interviewtask.Utils
import com.example.interviewtask.retrofit.MyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository to communicate with Network Services and Data Access Layer
 */
class ImageRepository @Inject constructor(private val myApi: MyApi) {

    suspend fun getViralPost(page: Int = 0): Flow<ResultWrapper<ViralImage>> {
        return flow {
            val response = myApi.getViralGallery(page)

            if (response.isSuccessful) {
                response.body()?.let { emit(ResultWrapper.Success(it)) }
            } else {
                val err = NetworkHelper.ErrorResponse()
                err.message = Utils.convertString(R.string.error)
                emit(ResultWrapper.GenericError(error = err))
            }
        }
    }

    suspend fun getSearchedData(q: String): Flow<ResultWrapper<ViralImage>> {
        return flow {
            val response = myApi.getSearchGallery(q)

            if (response.isSuccessful) {
                response.body()?.let { emit(ResultWrapper.Success(it)) }
            } else {
                val err = NetworkHelper.ErrorResponse()
                err.message = Utils.convertString(R.string.error)
                emit(ResultWrapper.GenericError(error = err))
            }
        }
    }
}