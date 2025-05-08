package com.example.intellireview_research_paper.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellireview_research_paper.data.PaperRepositoryImpl

import kotlinx.coroutines.launch

class CreatePostViewModel : ViewModel() {
    var title = mutableStateOf("")
    var authors = mutableStateOf("")
    var year = mutableStateOf(0)
    var pdfUrl = mutableStateOf<String?>(null)
    var uploadedBy = mutableStateOf("")
    var category = mutableStateOf("")
    var averageRating = mutableStateOf(0.0)
    var reviewCount = mutableStateOf(0)

    private val paperRepository = PaperRepositoryImpl()

    fun createPaper() {
        if (title.value.isNotEmpty() &&
            authors.value.isNotEmpty() &&
            category.value.isNotEmpty() &&
            pdfUrl.value != null
        ) {
            viewModelScope.launch {
                try {
                    paperRepository.createPaper(
                        title.value,
                        authors.value,
                        year.value,
                        pdfUrl.value!!,
                        uploadedBy.value,
                        category.value,
                        averageRating.value,
                        reviewCount.value
                    )
                    // Reset fields on success
                    title.value = ""
                    authors.value = ""
                    year.value = 0
                    pdfUrl.value = null
                    uploadedBy.value = ""
                    category.value = ""
                    averageRating.value = 0.0
                    reviewCount.value = 0
                } catch (e: Exception) {
                    // TODO: show error message
                }
            }
        } else {
            // TODO: show validation error
        }
    }

    fun updatePaper(paperId: String) {
        if (title.value.isNotEmpty() &&
            authors.value.isNotEmpty() &&
            category.value.isNotEmpty() &&
            pdfUrl.value != null
        ) {
            viewModelScope.launch {
                try {
                    paperRepository.updatePaper(
                        paperId,
                        title.value,
                        authors.value,
                        year.value,
                        pdfUrl.value!!,
                        uploadedBy.value,
                        category.value,
                        averageRating.value,
                        reviewCount.value
                    )
                    // TODO: handle success
                } catch (e: Exception) {
                    // TODO: handle error
                }
            }
        }
    }
}

