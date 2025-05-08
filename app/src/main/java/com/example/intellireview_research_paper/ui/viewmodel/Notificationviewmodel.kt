package com.example.intellireview_research_paper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellireview_research_paper.data.repository.NotificationRepositoryImpl
import com.example.intellireview_research_paper.model.notificationmodel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class NotificationUiState {
    object Loading : NotificationUiState()
    data class Success(val notifications: List<notificationmodel>) : NotificationUiState()
    data class Error(val message: String) : NotificationUiState()
    object Idle : NotificationUiState()
}

class NotificationViewModel(
    private val repository: NotificationRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotificationUiState>(NotificationUiState.Idle)
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    private val _notificationCreated = MutableSharedFlow<notificationmodel>()
    val notificationCreated: SharedFlow<notificationmodel> = _notificationCreated.asSharedFlow()

    fun fetchNotifications() {
        viewModelScope.launch {
            _uiState.value = NotificationUiState.Loading
            try {
                val result = repository.getNotification()
                _uiState.value = NotificationUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = NotificationUiState.Error(e.localizedMessage ?: "Error fetching notifications")
            }
        }
    }

    fun createNotification(title: String, message: String) {
        viewModelScope.launch {
            try {
                val created = repository.createNotification(title, message)
                _notificationCreated.emit(created)
                fetchNotifications()
            } catch (e: Exception) {
                _uiState.value = NotificationUiState.Error(e.localizedMessage ?: "Error creating notification")
            }
        }
    }

    fun editNotification(id: String, title: String, message: String) {
        viewModelScope.launch {
            try {
                repository.EditNotification(id, title, message)
                fetchNotifications()
            } catch (e: Exception) {
                _uiState.value = NotificationUiState.Error(e.localizedMessage ?: "Error editing notification")
            }
        }
    }
}
