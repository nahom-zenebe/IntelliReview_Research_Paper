package com.example.intellireview_research_paper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellireview_research_paper.data.mapper.ReviewRepository
import com.example.intellireview_research_paper.model.reviewmodel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ReviewUiState {
    object Loading : ReviewUiState()
    data class Success(val reviews: List<reviewmodel>) : ReviewUiState()
    data class Error(val message: String) : ReviewUiState()
}

class ReviewViewModel(private val repository: ReviewRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ReviewUiState>(ReviewUiState.Loading)
    val uiState: StateFlow<ReviewUiState> = _uiState

    fun fetchReviews() {
        viewModelScope.launch {
            _uiState.value = ReviewUiState.Loading
            try {
                val reviews = repository.getReview()
                _uiState.value = ReviewUiState.Success(reviews)
            } catch (e: Exception) {
                _uiState.value = ReviewUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun createReview(paperId: String, userId: String, rating: Number, comment: String) {
        viewModelScope.launch {
            try {
                repository.createReview(paperId, userId, rating, comment)
                fetchReviews() // Refresh after creation
            } catch (e: Exception) {
                _uiState.value = ReviewUiState.Error(e.message ?: "Failed to create review")
            }
        }
    }

    fun editReview(reviewId: String, title: String, message: String) {
        viewModelScope.launch {
            try {
                repository.EditReview(reviewId, title, message)
                fetchReviews() // Refresh after edit
            } catch (e: Exception) {
                _uiState.value = ReviewUiState.Error(e.message ?: "Failed to edit review")
            }
        }
    }
}
