package com.example.edrank_app.api

import com.example.edrank_app.models.TeachersForFeedbackRequest
import com.example.edrank_app.models.TeachersForFeedbackResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbacksAPI {
    @POST("/api/v1/get-feedback-teachers")
    suspend fun getFeedbackTeachers(@Body teachersForFeedbackRequest: TeachersForFeedbackRequest) : Response<TeachersForFeedbackResponse>
}