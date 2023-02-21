package com.example.interviewtask.models.ImageModel

import java.io.Serializable


data class Data(
    val accountId: Int,
    val accountUrl: String,
//    val adConfig: AdConfig,
    val adType: Int,
    val adUrl: String,
    val commentCount: Int,
    val cover: String,
    val coverHeight: Int,
    val coverWidth: Int,
    val datetime: Int,
    val description: String,
    val downs: Int,
    val favorite: Boolean,
    val favoriteCount: Int,
    val id: String,
    val images: List<ImageXX>?,
    val imagesCount: Int,
    val inGallery: Boolean,
    val inMostViral: Boolean,
    val includeAlbumAds: Boolean,
    val isAd: Boolean,
    val isAlbum: Boolean,
    val layout: String,
    val link: String,
    val nsfw: Boolean,
    val points: Int,
    val privacy: String,
    val score: Int,
    val section: String,
    val tags: List<Tag>,
    val title: String,
    val topic: Any,
    val topicId: Any,
    val ups: Int,
    val views: Int,
    val vote: Any
): Serializable