package com.example.edrank_app.models

data class User(
    val cid: Int,
    val email: String,
    val id: Int,
    val is_active: Boolean,
    val name: String
)