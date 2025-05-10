package com.example.intellireview_research_paper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellireview_research_paper.data.repository.CategoryRepositoryImpl
import com.example.intellireview_research_paper.model.categorymodel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class CategoryUiState {
    object Loading : CategoryUiState()
    data class Success(val categories: List<categorymodel>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
    object Idle : CategoryUiState()
}

class CategoryViewModel(
    private val repository: CategoryRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Idle)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    private val _categoryCreated = MutableSharedFlow<categorymodel>()
    val categoryCreated: SharedFlow<categorymodel> = _categoryCreated.asSharedFlow()

    fun fetchCategories() {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            try {
                val categories = repository.getCategory()
                _uiState.value = CategoryUiState.Success(categories)
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun createCategory(name: String, description: String) {
        viewModelScope.launch {
            try {
                val created = repository.createCategory(name, description)
                _categoryCreated.emit(created)
                fetchCategories()
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(e.localizedMessage ?: "Creation failed")
            }
        }
    }

    fun searchCategory(input: String) {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            try {
                val result = repository.searchCategory(input)
                _uiState.value = CategoryUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(e.localizedMessage ?: "Search failed")
            }
        }
    }

    fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            try {
                repository.deleteCategory(categoryId)
                fetchCategories()
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(e.localizedMessage ?: "Delete failed")
            }
        }
    }

    fun editCategory(categoryId: String, name: String, description: String) {
        viewModelScope.launch {
            try {
                repository.EditCategory(categoryId, name, description)
                fetchCategories()
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(e.localizedMessage ?: "Edit failed")
            }
        }
    }
}
