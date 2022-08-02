package com.example.edrank_app.models

data class ChangePasswordRequest(
    val old_password: String,
    val new_password: String
)