package com.example.edrank_app.api

import com.example.edrank_app.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeacherAPI {

    @GET("/api/v1/get-my-text-feedbacks")
    suspend fun teacherMyFeedbacks(): Response<TeacherFeedbackResponse>

    @GET("/api/v1/get-my-rank/{type}")
    suspend fun teacherMyRanks(@Path("type") type: String): Response<TeacherAllRanksResponse>

    @POST("/api/v1/sa-graph/:{type}")
    suspend fun getSaGraph(@Path("type") type: String, @Body graphSARequest: GraphSARequest) : Response<GraphSAResponse>
}