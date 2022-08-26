package com.example.edrank_app.models

data class FeedbackQuestionsResponse(
        val `data`: FeedbackQuestions,
        val message: String,
        val error: String,
        val drive_id : Int
)