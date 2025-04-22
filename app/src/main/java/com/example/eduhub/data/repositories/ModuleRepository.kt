package com.example.eduhub.data.repositories

import com.example.eduhub.data.local.dao.ModuleDao
import com.example.eduhub.data.models.Module
import com.example.eduhub.ui.modules.list.ModuleItemState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ModuleRepositoryInterface {
    suspend fun getAllModules(): List<ModuleItemState>

}

class ModuleRepository(
    private val moduleDao: ModuleDao,
): ModuleRepositoryInterface {
    override suspend fun getAllModules(): List<ModuleItemState> {
        return withContext(Dispatchers.IO) {
//            val userId = userPreferences.getCurrentUserId()
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