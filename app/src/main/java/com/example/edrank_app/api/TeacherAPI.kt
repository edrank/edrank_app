package com.example.edrank_app.api

import com.example.edrank_app.models.TopTeachersRequest
import com.example.edrank_app.models.TopTeachersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TeacherAPI {

    @POST("/api/v1/top-3-teachers")
    suspend fun topTeachers(@Body topTeachersRequest: TopTeachersRequest) : Response<TopTeachersResponse>

}