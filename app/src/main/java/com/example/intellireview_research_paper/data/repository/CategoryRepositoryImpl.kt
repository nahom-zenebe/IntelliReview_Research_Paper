package com.example.intellireview_research_paper.data.repository

import com.example.intellireview_research_paper.data.mapper.CategoryRepository
import com.example.intellireview_research_paper.data.remote.CategoryApi
import com.example.intellireview_research_paper.model.categorymodel
import retrofit2.HttpException
import java.io.IOException

class CategoryRepositoryImpl(
    private val api: CategoryApi
) : CategoryRepository {

    override suspend fun getCategory(): List<categorymodel> {
        return try {
            val response = api.getCategories()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw e
        }
    }

    override suspend fun createCategory(name: String, description: String): categorymodel {
        val response = api.createCategory(name, description)
        if (response.isSuccessful) {
            return response.body() ?: throw IllegalStateException("No category returned")
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun searchCategory(inputdata: String): List<categorymodel> {
        val response = api.searchCategory(inputdata)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun deleteCategory(categoryId: String) {
        val response = api.deleteCategory(categoryId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
    }

    override suspend fun EditCategory(categoryId: String, name: String, description: String): categorymodel {
        val response = api.editCategory(categoryId, name, description)
        if (response.isSuccessful) {
            return response.body() ?: throw IllegalStateException("No edited category returned")
        } else {
            throw HttpException(response)
        }
    }
}
