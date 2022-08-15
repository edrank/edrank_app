package com.example.edrank_app.models

data class TopCollegesRequest(
    var city: String,
    var request_type: String,
    var state: String,
    var n: Int
)
