package com.example.edrank_app.models

data class PostFeedbackRequest(
    val college_id: Int,
    val drive_id: Int,
    val feedback: Feedback,
    val teacher_id: Int
)