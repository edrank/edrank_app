package com.example.edrank_app.api

import android.util.Log
import com.example.edrank_app.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = tokenManager.getToken()
        val tenant = tokenManager.getTenant()

        Log.d("fjkshk",tenant.toString())
        request.addHeader("x-edrank-tenant-type", tenant!!)

//        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}