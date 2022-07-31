package com.example.edrank_app.api

import com.example.edrank_app.models.LoginRequest
import com.example.edrank_app.models.LoginResponse
import com.example.edrank_app.utils.TokenManager
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject

interface LoginAPI {

    @POST("/api/v1/login")
    suspend fun login(@Header ("x-edrank-tenant-type") tenant : String , @Body loginRequest: LoginRequest ) : Response<LoginResponse>
}