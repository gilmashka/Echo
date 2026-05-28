package com.echo.features.category.data.local

import javax.inject.Inject

class LocalCategoryDataSource @Inject constructor(
    private val dao: CategoryDao
) {
    suspend fun getCached(): List<CategoryEntity> = dao.getAll()

    suspend fun cache(entities: List<CategoryEntity>) {
        dao.deleteAll()
        dao.insertAll(entities)
    }
}