package com.example.edrank_app.models

data class CollegeRankRequest(
    var cid: Int,
    var city: String,
    var request_type: String,
    var state: String
)