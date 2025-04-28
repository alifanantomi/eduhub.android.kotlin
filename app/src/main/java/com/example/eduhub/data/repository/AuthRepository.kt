package com.example.eduhub.data.repository
import android.util.Log
import com.example.eduhub.data.api.ApiService
import com.example.eduhub.data.api.model.request.LoginRequest
import com.example.eduhub.data.api.model.request.RegisterRequest
import com.example.eduhub.data.api.model.response.RegisterResponse
import com.example.eduhub.data.local.preferences.UserPreferences
import com.example.eduhub.data.mapper.toUser
import com.example.eduhub.data.model.User
import com.example.eduhub.data.model.Result
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreferences
) : AuthRepositoryInterface {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = apiService.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    val user = authResponse.data.user.toUser()

                    userPreference.saveAuthToken(authResponse.data.token)
                    userPreference.saveUser(user)

                    Result.Success(user)
                } ?: Result.Error(Exception("Login failed"))
            } else {
                val errorJson = response.errorBody()?.string()

                val errorMessage = if (errorJson != null) {
                    try {
                        val jsonObject = JSONObject(errorJson)
                        jsonObject.getString("error")
                    } catch (e: Exception) {
                        "Unknown error"
                    }
                } else {
                    "Unknown error"
                }

                Result.Error(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
    ): Result<RegisterResponse> {
        return try {
            val response = apiService.register(RegisterRequest(email, password, name))

            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    return Result.Success(authResponse)
                } ?: return Result.Error(Exception("Registration failed"))
            } else {
                val errorJson = response.errorBody()?.string()

                val errorMessage = if (errorJson != null) {
                    try {
                        val jsonObject = JSONObject(errorJson)
                        jsonObject.getString("error")
                    } catch (e: Exception) {
                        "Unknown error"
                    }
                } else {
                    "Unknown error"
                }

                Result.Error(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun logout() {
        userPreference.clearUserData()
    }

    override fun isLoggedIn(): Boolean {
        return runBlocking {
            userPreference.authTokenFlow.firstOrNull()?.isNotEmpty() == true
        }
    }
}