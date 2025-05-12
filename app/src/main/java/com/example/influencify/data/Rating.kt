package com.example.influencify.data

data class Rating(
    val userUid: String = "",
    val rating: Float = 0f,
    val timestamp: Long = 0L
)