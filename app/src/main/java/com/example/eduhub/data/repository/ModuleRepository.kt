package com.example.eduhub.data.repository

import com.example.eduhub.data.api.ApiService
import com.example.eduhub.data.api.model.response.ModuleDetail
import com.example.eduhub.data.api.model.response.ModuleResponse
import com.example.eduhub.data.model.Result
import javax.inject.Inject

class ModuleRepository @Inject constructor(
    private val apiService: ApiService
) : ModuleRepositoryInterface {

    override suspend fun getAllModules(): Result<ModuleResponse> {
        return try {
            val response = apiService.getModules()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception("Failed to fetch modules"))
            } else {
                Result.Error(Exception("Failed to fetch modules"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getModuleById(id: String): Result<ModuleDetail> {
        return try {
            val response = apiService.getDetailModule(id)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception("Failed to fetch module"))
            } else {
                Result.Error(Exception("Failed to fetch module"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}