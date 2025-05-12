// ui/viewmodel/BookmarkViewModel.kt

package com.example.intellireview_research_paper.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.intellireview_research_paper.model.paperModel

class BookmarkViewModel : ViewModel() {
    private val _bookmarkedPapers = MutableStateFlow<List<paperModel>>(emptyList())
    val bookmarkedPapers: StateFlow<List<paperModel>> get() = _bookmarkedPapers

    fun toggleBookmark(paper: paperModel) {
        val current = _bookmarkedPapers.value
        Log.d("BookmarkVM", "Toggling bookmark for ${paper.paperId}. Currently: ${current.map { it.paperId }}")

        val updated = if (current.any { it.paperId == paper.paperId }) {
            current.filter { it.paperId != paper.paperId }.also {
                Log.d("BookmarkVM", "→ Removed ${paper.paperId}")
            }
        } else {
            current + paper.also {
                Log.d("BookmarkVM", "→ Added ${paper.paperId}")
            }
        }

        _bookmarkedPapers.value = updated
        Log.d("BookmarkVM", "New bookmarks list: ${updated.map { it.paperId }}")
    }
}
