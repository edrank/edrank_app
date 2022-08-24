package com.example.edrank_app.api

import com.example.edrank_app.models.FeedbackQuestionsResponse
import com.example.edrank_app.models.TeachersForFeedbackRequest
import com.example.edrank_app.models.TeachersForFeedbackResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackAPI {

    @POST("/feedback-questions/{type}")
    suspend fun getFeedbackQuestions(@Path("cId") cId: String): Response<FeedbackQuestionsResponse>

    @POST("/get-feedback-teachers/")
    suspend fun getTeachersForFeedback(@Body teachersForFeedbackRequest: TeachersForFeedbackRequest): Response<TeachersForFeedbackResponse>


}