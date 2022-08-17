package com.example.edrank_app.api

import com.example.edrank_app.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {

    @POST("/api/v1/change-password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest) : Response<ChangePasswordResponse>

    @GET("/api/v1/my-profile")
    suspend fun myProfile() : Response<MyProfileResponse>

    @GET("/api/v1/get-course/{cId}")
    suspend fun getCourse(@Path("cId") cId: String,) : Response<CourseResponse>

    @POST("/api/v1/top-n-teachers")
    suspend fun topTeachers(@Body topTeachersRequest: TopTeachersRequest) : Response<TopTeachersResponse>

    @POST("/api/v1/top-n-colleges")
    suspend fun topColleges(@Body topCollegesRequest: TopCollegesRequest) : Response<TopCollegesResponse>

    @POST("/api/v1/get-my-colleges-rank")
    suspend fun collegeRank(@Body collegeRankRequest: CollegeRankRequest) : Response<CollegeRankResponse>
}