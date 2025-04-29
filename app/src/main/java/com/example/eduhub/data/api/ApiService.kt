package com.example.eduhub.data.api

import com.example.eduhub.data.api.model.request.LoginRequest
import com.example.eduhub.data.api.model.request.RegisterRequest
import com.example.eduhub.data.api.model.response.AuthResponse
import com.example.eduhub.data.api.model.response.ModuleDetail
import com.example.eduhub.data.api.model.response.ModuleResponse
import com.example.eduhub.data.api.model.response.RegisterResponse
import com.example.eduhub.data.model.Module
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @GET("modules")
    suspend fun getModules(): Response<ModuleResponse>

    @GET("modules/{id}")
    suspend fun getDetailModule(@Path("id") id: String): Response<ModuleDetail>
}