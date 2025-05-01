package com.example.eduhub.data.repository

import com.example.eduhub.data.api.model.response.DetailModuleResponse
import com.example.eduhub.data.api.model.response.ModuleResponse
import com.example.eduhub.data.model.Result

interface ModuleRepositoryInterface {
    suspend fun getAllModules(): Result<ModuleResponse>?
    suspend fun getModuleById(id: String): Result<DetailModuleResponse>
}