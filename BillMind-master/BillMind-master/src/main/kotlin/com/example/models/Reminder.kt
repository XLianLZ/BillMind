package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Reminder(
    val id: Int,
    val message: String,
    val date: String,
    val endReminder: String,
    val idClient: Int
)
