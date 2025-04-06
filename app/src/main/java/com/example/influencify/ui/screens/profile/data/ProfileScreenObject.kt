package com.example.influencify.ui.screens.profile.data

import kotlinx.serialization.Serializable

@Serializable
data class ProfileScreenObject(
    val uid: String // Pass the user ID to fetch their profile data
) 