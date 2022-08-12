package com.example.edrank_app.api

import com.example.edrank_app.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {

    @POST("/api/v1/change-password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest) : Response<ChangePasswordResponse>

    @GET("/api/v1/my-profile")
    suspend fun teacherMyProfile() : Response<TeacherProfileResponse>

    @POST("/api/v1/top-n-teachers")
    suspend fun topTeachers(@Body topTeachersRequest: TopTeachersRequest) : Response<TopTeachersResponse>
}