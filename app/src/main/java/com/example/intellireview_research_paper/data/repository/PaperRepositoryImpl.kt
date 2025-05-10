package com.example.intellireview_research_paper.data.repository

import com.example.intellireview_research_paper.api.PaperApiClient
import com.example.intellireview_research_paper.data.mapper.PaperRepository
import com.example.intellireview_research_paper.model.paperModel
import com.example.intellireview_research_paper.util.toPlainRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PaperRepositoryImpl : PaperRepository {
    override suspend fun getPapers(): List<paperModel> =
        PaperApiClient.apiService.getPapers().body() ?: emptyList()

    override suspend fun createPaper(
        title: String,
        authors: String,
        year: Int,
        pdfUrl: String,
        uploadedBy: String,
        category: String,
        averageRating: Double,
        reviewCount: Int
    ): paperModel {
        val file = File(pdfUrl)
        val part = MultipartBody.Part.createFormData(
            "pdf",
            file.name,
            file.asRequestBody("application/pdf".toMediaTypeOrNull())
        )
        val resp = PaperApiClient.apiService.createPaper(
            title.toPlainRequestBody(),
            authors.toPlainRequestBody(),
            year.toString().toPlainRequestBody(),
            uploadedBy.toPlainRequestBody(),
            category.toPlainRequestBody(),
            part
        )
        return resp.body()!!
    }

    override suspend fun searchPaper(inputData: String): List<paperModel> =
        PaperApiClient.apiService.searchPaper(inputData).body() ?: emptyList()

    override suspend fun deletePaper(paperId: String) {
        PaperApiClient.apiService.deletePaper(paperId)
    }

    override suspend fun updatePaper(
        paperId: String,
        title: String,
        authors: String,
        year: Int,
        pdfUrl: String,
        uploadedBy: String,
        category: String,
        averageRating: Double,
        reviewCount: Int
    ): paperModel {
        val file = File(pdfUrl)
        val part = MultipartBody.Part.createFormData(
            "pdf",
            file.name,
            file.asRequestBody("application/pdf".toMediaTypeOrNull())
        )
        val resp = PaperApiClient.apiService.updatePaper(
            paperId,
            title.toPlainRequestBody(),
            authors.toPlainRequestBody(),
            year.toString().toPlainRequestBody(),
            uploadedBy.toPlainRequestBody(),
            category.toPlainRequestBody(),
            part
        )
        return resp.body()!!
    }
}
