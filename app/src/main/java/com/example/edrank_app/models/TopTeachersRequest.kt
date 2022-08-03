package com.example.edrank_app.models

data class TopTeachersRequest(
    val cid: Int,
    val city: String,
    val request_type: String,
    val state: String
)