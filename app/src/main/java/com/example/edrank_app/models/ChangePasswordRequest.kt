package com.example.edrank_app.models

data class ChangePasswordRequest(
    val new_password: String,
    val old_password: String
)