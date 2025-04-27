package com.example.eduhub.data.repository

import com.example.eduhub.data.local.dao.ModuleDao
import com.example.eduhub.data.local.preferences.UserPreferences
import com.example.eduhub.ui.modules.list.ModuleItemState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ModuleRepositoryInterface {
    suspend fun getAllModules(): List<ModuleItemState>

}

class ModuleRepository(
    private val moduleDao: ModuleDao,
    private val userPreference: UserPreferences
): ModuleRepositoryInterface {
    override suspend fun getAllModules(): List<ModuleItemState> {
        return withContext(Dispatchers.IO) {
//            val userId = userPreference.getCurrentUserId()
            val modules = moduleDao.getAllModules()

            modules.map { module ->
                ModuleItemState(
                    title = module.title,
                    image = module.imageUrl,
                    summary = module.summary,
                    content = module.content
                )
            }
        }
    }
}