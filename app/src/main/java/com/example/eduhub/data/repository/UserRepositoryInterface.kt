package com.example.eduhub.data.repository

import com.example.eduhub.data.api.model.response.UpdateUserResponse
import com.example.eduhub.data.model.Result
import java.io.File

interface UserRepositoryInterface {
    suspend fun updateUserProfile(user: String, image: String): Result<UpdateUserResponse>
    suspend fun uploadProfileImage(imageFile: File): Result<String>
}