package com.example.influencify.ui.screens.favorites.data

import kotlinx.serialization.Serializable

@Serializable
data class FavoritesScreenObject(
    val uid: String // Pass the user ID to fetch their favorites
)