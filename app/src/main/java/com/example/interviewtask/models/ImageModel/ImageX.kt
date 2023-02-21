package com.example.interviewtask.models.ImageModel

import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class ImageX(
    val accountId: String,
    val accountUrl: String,
    val adType: Int,
    val adUrl: String,
    val animated: Boolean,
    val bandwidth: Long,
    val commentCount: String,
    val datetime: Int,
    val description: String,
    val downs: String,
    val edited: String,
    val favorite: Boolean,
    val favoriteCount: String,
    val gifv: String,
    val hasSound: Boolean,
    val height: Int,
    val hls: String,
    val id: String,
    val inGallery: Boolean,
    val inMostViral: Boolean,
    val isAd: Boolean,
    val link: String,
    val mp4: String,
    val mp4Size: Int,
    val nsfw: String,
    val points: String,
    val score: String,
    val section: String,
    val size: Int,
    val tags: List<String>,
    val title: String,
    val type: String,
    val ups: String,
    val views: Int,
    val vote: String,
    val width: Int
): Serializable