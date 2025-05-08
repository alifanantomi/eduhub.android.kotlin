package com.example.eduhub.data.repository

import com.example.eduhub.data.api.ApiService
import com.example.eduhub.data.api.apiCallToResult
import com.example.eduhub.data.api.model.response.DetailModuleResponse
import com.example.eduhub.data.api.model.response.ModuleResponse
import com.example.eduhub.data.model.Result
import javax.inject.Inject
import retrofit2.HttpException

class ModuleRepository @Inject constructor(
    private val apiService: ApiService
) : ModuleRepositoryInterface {

    override suspend fun getAllModules(): Result<ModuleResponse> =
        apiCallToResult {
            val res = apiService.getModules()
            if (!res.isSuccessful) throw HttpException(res)
            res.body() ?: throw Exception("No data")
        }

    override suspend fun getModuleById(id: String): Result<DetailModuleResponse> =
        apiCallToResult {
            val res = apiService.getDetailModule(id)
            if (!res.isSuccessful) throw HttpException(res)
            res.body() ?: throw Exception("No data")
        }
}