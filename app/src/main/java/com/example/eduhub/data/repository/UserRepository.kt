package com.example.eduhub.data.repository

import com.example.eduhub.data.api.ApiService
import com.example.eduhub.data.api.apiCallToResult
import com.example.eduhub.data.api.model.request.UserRequest
import com.example.eduhub.data.api.model.response.UpdateUserResponse
import com.example.eduhub.data.model.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) : UserRepositoryInterface {

    override suspend fun updateUserProfile(name: String, image: String): Result<UpdateUserResponse> =
        apiCallToResult {
            val res = apiService.updateUserProfile(UserRequest(name, image))
            if (!res.isSuccessful) throw HttpException(res)
            res.body() ?: throw Exception("No data")
        }

    override suspend fun uploadProfileImage(imageFile: File): Result<String> =
        apiCallToResult {
            // Create multipart request for file
            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

            // Make API call
            val res = apiService.uploadFile(filePart)
            if (!res.isSuccessful) throw HttpException(res)
            res.body()?.url ?: throw Exception("No image URL returned")
        }
}