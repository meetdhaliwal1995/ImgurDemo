package com.example.interviewtask.models.ImageModel

import androidx.annotation.Keep


@Keep
data class Tag(
    val accent: String,
    val backgroundHash: String,
    val backgroundIsAnimated: Boolean,
    val description: String,
    val displayName: String,
    val followers: Int,
    val following: Boolean,
    val isPromoted: Boolean,
    val isWhitelisted: Boolean,
    val logoDestinationUrl: Any,
    val logoHash: Any,
    val name: String,
    val thumbnailHash: Any,
    val thumbnailIsAnimated: Boolean,
    val totalItems: Int
)