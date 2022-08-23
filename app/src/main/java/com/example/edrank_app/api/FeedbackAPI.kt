package com.example.edrank_app.api

import com.example.edrank_app.models.CourseResponse
import com.example.edrank_app.models.FeedbackQuestionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackAPI {

    @POST("/feedback-questions/{type}")
    suspend fun getFeedbackQuestions(@Path("cId") cId: String,) : Response<FeedbackQuestionsResponse>
}