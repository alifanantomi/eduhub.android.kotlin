package com.example.eduhub.data.api

import com.example.eduhub.data.api.model.request.BookmarkRequest
import com.example.eduhub.data.api.model.request.LoginRequest
import com.example.eduhub.data.api.model.request.RegisterRequest
import com.example.eduhub.data.api.model.request.UserRequest
import com.example.eduhub.data.api.model.response.AuthResponse
import com.example.eduhub.data.api.model.response.BookmarkResponse
import com.example.eduhub.data.api.model.response.DetailModuleResponse
import com.example.eduhub.data.api.model.response.ModuleResponse
import com.example.eduhub.data.api.model.response.RegisterResponse
import com.example.eduhub.data.api.model.response.TopicResponse
import com.example.eduhub.data.api.model.response.UpdateUserResponse
import com.example.eduhub.data.api.model.response.UploadResponse
import okhttp3.MultipartBody
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
    suspend fun getDetailModule(@Path("id") id: String): Response<DetailModuleResponse>

    @GET("topics")
    suspend fun getTopics(): Response<TopicResponse>

    @GET("bookmarks")
    suspend fun getBookmarks(): Response<BookmarkResponse>

    @POST("bookmarks")
    suspend fun createBookmark(@Body bookmarkRequest: BookmarkRequest): Response<BookmarkResponse>

    @PUT("users/profile")
    suspend fun updateUserProfile(@Body userRequest: UserRequest): Response<UpdateUserResponse>

    @Multipart
    @POST("upload")
    suspend fun uploadFile(@Part file: MultipartBody.Part): Response<UploadResponse>
}