package com.example.edrank_app.di

import com.example.edrank_app.api.*
import com.example.edrank_app.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(authInterceptor: AuthInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesLoginAPI(retrofitBuilder: Retrofit.Builder) : LoginAPI {
        return retrofitBuilder.build().create(LoginAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): UserAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesTeacherAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): TeacherAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(TeacherAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesParentAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): ParentAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(ParentAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesFeedbackAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): FeedbackAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(FeedbackAPI::class.java)
    }

}