package com.example.intellireview_research_paper.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellireview_research_paper.data.repository.PaperRepositoryImpl
import kotlinx.coroutines.launch

class CreatePostViewModel : ViewModel() {
    val title         = mutableStateOf("")
    val authors       = mutableStateOf("")
    val year          = mutableStateOf(0)
    val pdfUrl        = mutableStateOf<String?>(null)
    val uploadedBy    = mutableStateOf("")
    val category      = mutableStateOf("")
    val averageRating = mutableStateOf(0.0)
    val reviewCount   = mutableStateOf(0)

    private val repo = PaperRepositoryImpl()
    private val TAG  = "CreatePostViewModel"

    fun createPaper() {
        if (title.value.isBlank() ||
            authors.value.isBlank() ||
            category.value.isBlank() ||
            pdfUrl.value.isNullOrBlank()
        ) {
            Log.w(TAG, "Validation failed: missing fields")
            return
        }

        viewModelScope.launch {
            try {
                Log.d(TAG, "Starting upload for file: ${pdfUrl.value}")
                repo.createPaper(
                    title.value,
                    authors.value,
                    year.value,
                    pdfUrl.value!!,
                    uploadedBy.value,
                    category.value,
                    averageRating.value,
                    reviewCount.value
                )
                Log.d(TAG, "Upload successful")

                // Reset state on success
                title.value         = ""
                authors.value       = ""
                year.value          = 0
                pdfUrl.value        = null
                uploadedBy.value    = ""
                category.value      = ""
                averageRating.value = 0.0
                reviewCount.value   = 0
            } catch (e: Exception) {
                Log.e(TAG, "Upload failed", e)
            }
        }
    }
}
