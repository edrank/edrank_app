package com.example.edrank_app.models

data class LoginData(
    val access_token: String,
    val tenant_id: Int,
    val tenant_type: String,
    var user: User
)