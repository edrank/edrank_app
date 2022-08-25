package com.example.edrank_app.models

data class FeedbackQuestions(
    val questions: List<Question>,
    val type: String,
    val drive_id : Int
)