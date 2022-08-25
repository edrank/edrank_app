package com.example.edrank_app.api

import com.example.edrank_app.models.TeacherAllRanksResponse
import com.example.edrank_app.models.TeacherFeedbackResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeacherAPI {

    @GET("/api/v1/get-my-text-feedbacks")
    suspend fun teacherMyFeedbacks(): Response<TeacherFeedbackResponse>

    @GET("/api/v1/get-my-rank/{type}")
    suspend fun teacherMyRanks(@Path("type") type: String): Response<TeacherAllRanksResponse>

}