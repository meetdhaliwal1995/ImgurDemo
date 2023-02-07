package com.example.interviewtask.models

data class ModelResponse(
    val cid: String,
    val description: String,
    val error: Boolean,
    val message: String,
    val result: List<Any>,
    val statusCode: Int,
    val success: Boolean
)

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Int,
    val avgColor: String,
    val src: Map<String, String>,
    val liked: Boolean,
    val alt: String
)

data class PhotosResponse(
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>,
    val totalResults: Int,
    val nextPage: String
)