package com.example.edrank_app.api

import com.example.edrank_app.models.ChildrenOfParentResponse
import retrofit2.Response
import retrofit2.http.GET

interface ParentAPI {
    @GET("/api/v1/get-my-students")
    suspend fun getChildren() : Response<ChildrenOfParentResponse>
}