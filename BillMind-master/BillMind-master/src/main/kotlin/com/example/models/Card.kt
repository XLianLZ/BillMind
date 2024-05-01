package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: Int,
    val cardName: String,
    val cardNumber: String,
    val expirationDate: String,
    val idClient: Int,
)
