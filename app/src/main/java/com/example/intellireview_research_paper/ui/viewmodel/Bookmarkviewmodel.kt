package com.example.intellireview_research_paper.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.intellireview_research_paper.model.paperModel

class BookmarkViewModel : ViewModel() {
    private val _bookmarkedPapers = mutableStateOf<List<paperModel>>(emptyList())
    val bookmarkedPapers: State<List<paperModel>> = _bookmarkedPapers

    fun toggleBookmark(paper: paperModel) {
        _bookmarkedPapers.value = if (_bookmarkedPapers.value.any { it.paperId == paper.paperId }) {
            _bookmarkedPapers.value.filter { it.paperId != paper.paperId }
        } else {
            _bookmarkedPapers.value + paper
        }
    }

}