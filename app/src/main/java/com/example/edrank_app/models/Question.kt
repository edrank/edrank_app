package com.example.edrank_app.models

data class Question(
    val created_at: String,
    val id: Int,
    val is_active: String,
    val option_1: String,
    val option_2: String,
    val option_3: String,
    val option_4: String,
    val option_5: String,
    val title: String,
    val type: String,
    val updated_at: String
)