package com.example.models

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Debt(
    val id: Int,
    val name: String,
    val expiration: String,
    val amount: String,
    val description: String,
    val relevance: String,
    val idClient: Int,
)



