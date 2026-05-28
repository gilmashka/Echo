package com.echo.features.category.data.repositoryImpls

import com.echo.features.category.api.CategoryApi
import com.echo.features.category.data.local.CategoryEntity
import com.echo.features.category.data.local.LocalCategoryDataSource
import com.echo.features.category.data.models.CategoryPreferenceDto
import com.echo.features.category.data.models.PreferenceType
import com.echo.features.category.domain.repositories.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
    private val local: LocalCategoryDataSource
): CategoryRepository{

    override suspend fun getCategories(): Result<List<CategoryPreferenceDto>> {
        val cached = local.getCached().map { it.toDto() }
        return try {
            val response = api.getUserCategoryList()
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                local.cache(body.map { it.toEntity() })
                Result.success(body)
            } else Result.success(cached.ifEmpty { emptyList() })
        } catch (e: Exception) {
            if (cached.isNotEmpty()) Result.success(cached)
            else Result.failure(e)
        }
    }

    override suspend fun setPreference(dto: CategoryPreferenceDto): Result<Unit> {
        val entities = local.getCached().map {
            if (it.id == dto.id) it.copy(preference = dto.preferenceType?.name) else it
        }
        local.cache(entities)

        return try {
            val response = api.setUserCategoryPreference(dto)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }

    private fun CategoryPreferenceDto.toEntity() = CategoryEntity(
        id = id, name = name, slug = slug,
        preference = preferenceType?.name
    )

    private fun CategoryEntity.toDto() = CategoryPreferenceDto(
        id = id, name = name, slug = slug,
        preferenceType = preference?.let { PreferenceType.valueOf(it) }
    )
}