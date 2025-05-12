package com.example.intellireview_research_paper.model

import com.google.gson.annotations.SerializedName

data class paperModel(
    @SerializedName("_id")
    val paperId: String? = null,

    val title: String? = null,
    val authors: List<String> = emptyList(),
    val year: Int = 0,
    val pdfUrl: String? = null,
    val uploadedBy: String? = null,
    val category: String? = null,
    val averageRating: Double = 0.0,
    val reviewCount: Int = 0,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
