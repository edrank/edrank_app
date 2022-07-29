package com.example.edrank_app.models

data class Data(
    val access_token: String,
    val tenant_id: Int,
    val tenant_type: String,
    val user: User
)