package com.example.eduhub.data.repository

import com.example.eduhub.data.api.ApiService
import com.example.eduhub.data.api.model.response.ModuleDetail
import com.example.eduhub.data.api.model.response.ModuleResponse
import com.example.eduhub.data.local.dao.ModuleDao
import com.example.eduhub.data.local.preferences.UserPreferences
import com.example.eduhub.data.model.Result
import com.example.eduhub.ui.modules.list.ModuleItemState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ModuleRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreferences,
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