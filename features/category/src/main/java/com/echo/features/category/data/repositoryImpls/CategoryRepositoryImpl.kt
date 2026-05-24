package com.echo.features.category.data.repositoryImpls

import androidx.compose.ui.graphics.RectangleShape
import com.echo.features.category.api.CategoryApi
import com.echo.features.category.data.models.CategoryPreferenceDto
import com.echo.features.category.domain.repositories.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi
): CategoryRepository{

    override suspend fun getCategories(): Result<List<CategoryPreferenceDto>> {
        return try {
            val response = api.getUserCategoryList()
            if (response.isSuccessful){
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Ошибка загрузки категорий: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setPreference(dto: CategoryPreferenceDto): Result<Unit> {
        return try {
            val response = api.setUserCategoryPreference(dto)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка при сохранении, попробуйте позже: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}