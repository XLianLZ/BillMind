package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Client(
    val id: Int,
    val name: String,
    val lastName: String,
    val mail: String,
    val phone: String,
)
