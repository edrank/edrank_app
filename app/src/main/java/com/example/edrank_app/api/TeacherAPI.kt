package com.example.edrank_app.api

import com.example.edrank_app.models.TeacherFeedbackResponse
import retrofit2.Response
import retrofit2.http.GET

interface TeacherAPI {

    @GET("/api/v1/get-my-text-feedbacks")
    suspend fun teacherMyFeedbacks(): Response<TeacherFeedbackResponse>
}