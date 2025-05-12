package com.example.intellireview_research_paper.data.repository

import com.example.intellireview_research_paper.data.mapper.ReviewRepository
import com.example.intellireview_research_paper.data.remote.ReviewApiClient
import com.example.intellireview_research_paper.model.reviewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRepositoryImpl : ReviewRepository {

    private val api = ReviewApiClient.apiService

    override suspend fun getReview(): List<reviewmodel> = withContext(Dispatchers.IO) {
        val response = api.getReviews()
        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch reviews: ${response.message()}")
        }
    }

    override suspend fun createReview(
        paperId: String,
        userId: String,
        rating: Number,
        comment: String
    ): reviewmodel = withContext(Dispatchers.IO) {
        val response = api.createReview(paperId, userId, rating.toInt(), comment)
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Failed to create review: Empty response body")
        } else {
            throw Exception("Failed to create review: ${response.message()}")
        }
    }

    override suspend fun EditReview(
        notificationId: String,
        title: String,
        message: String
    ): reviewmodel = withContext(Dispatchers.IO) {
        val response = api.editReview(notificationId, title, message)
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Failed to edit review: Empty response body")
        } else {
            throw Exception("Failed to edit review: ${response.message()}")
        }
    }
}
