package com.example.interviewtask.retrofit

import com.example.interviewtask.helper.Constant
import com.example.interviewtask.models.ImageModel.ViralImage
import com.example.interviewtask.models.ModelResponse
import okhttp3.MultipartBody
import retrofit2.http.*
import retrofit2.Response

/**
 * Network API Interface
 */
interface MyApi {

    @Headers("Content-Type: application/json", "Authorization: ${Constant.IMGUR_TOKEN}")
    @GET("gallery/hot/viral/{page}.json")
    suspend fun getViralGallery(
        @Path("page") page: Int
    ): Response<ViralImage>

    @Headers("Content-Type: application/json", "Authorization: ${Constant.IMGUR_TOKEN}")
    @GET("gallery/search/")
    suspend fun getSearchGallery(
        @Query("q") q: String
    ): Response<ViralImage>

    @Multipart
    @Headers("Content-Type: application/json", "Authorization: ${Constant.IMGUR_TOKEN}")
    @POST("upload")
    suspend fun uploadImage(
//        @Part type: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): Response<ModelResponse>

}

