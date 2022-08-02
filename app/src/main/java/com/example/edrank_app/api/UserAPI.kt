package com.example.edrank_app.api

import com.example.edrank_app.models.ChangePasswordRequest
import com.example.edrank_app.models.ChangePasswordResponse
import dagger.Provides
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/api/v1/change-password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest) : Response<ChangePasswordResponse>
}