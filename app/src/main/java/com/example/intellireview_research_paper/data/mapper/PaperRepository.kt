package com.example.intellireview_research_paper.data.mapper

import com.example.intellireview_research_paper.model.paperModel

interface PaperRepository {
    suspend fun getPapers(): List<paperModel>
    suspend fun createPaper(
        title: String,
        authors: String,
        year: Int,
        pdfUrl: String,
        uploadedBy: String,
        category: String,
        averageRating: Double,
        reviewCount: Int
    ): paperModel

    suspend fun searchPaper(inputData: String): List<paperModel>
    suspend fun deletePaper(paperId: String)
    suspend fun updatePaper(
        paperId: String,
        title: String,
        authors: String,
        year: Int,
        pdfUrl: String,
        uploadedBy: String,
        category: String,
        averageRating: Double,
        reviewCount: Int
    ): paperModel
}