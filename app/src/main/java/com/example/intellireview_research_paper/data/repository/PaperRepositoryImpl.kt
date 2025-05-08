
package com.example.intellireview_research_paper.data

import com.example.intellireview_research_paper.api.PaperApiClient
import com.example.intellireview_research_paper.data.mapper.PaperRepository
import com.example.intellireview_research_paper.model.paperModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import toPlainRequestBody
import java.io.File

class PaperRepositoryImpl : PaperRepository {
    override suspend fun getPapers(): List<paperModel> {
        val response = PaperApiClient.apiService.getPapers()
        return response.body() ?: emptyList()
    }

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
        val pdfRequest = file.asRequestBody("application/pdf".toMediaTypeOrNull())
        val pdfPart = MultipartBody.Part.createFormData("pdf", file.name, pdfRequest)

        val response = PaperApiClient.apiService.createPaper(
            title.toPlainRequestBody(),
            authors.toPlainRequestBody(),
            year.toString().toPlainRequestBody(),
            uploadedBy.toPlainRequestBody(),
            category.toPlainRequestBody(),
            pdfPart
        )
        return response.body()!!
    }

    override suspend fun searchPaper(inputData: String): List<paperModel> {
        val response = PaperApiClient.apiService.searchPaper(inputData)
        return response.body() ?: emptyList()
    }

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
        val pdfRequest = file.asRequestBody("application/pdf".toMediaTypeOrNull())
        val pdfPart = MultipartBody.Part.createFormData("pdf", file.name, pdfRequest)

        val response = PaperApiClient.apiService.updatePaper(
            paperId,
            title.toPlainRequestBody(),
            authors.toPlainRequestBody(),
            year.toString().toPlainRequestBody(),
            uploadedBy.toPlainRequestBody(),
            category.toPlainRequestBody(),
            pdfPart
        )
        return response.body()!!
    }
}