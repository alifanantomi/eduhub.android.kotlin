package com.example.eduhub.data.local.dao

import com.example.eduhub.data.models.Module

interface ModuleDao {
    suspend fun getAllModules(): List<Module>

    suspend fun getModuleById(moduleId: String): Module?

    suspend fun searchModules(query: String): List<Module>
}