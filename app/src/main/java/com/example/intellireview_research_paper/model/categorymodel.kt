package com.example.intellireview_research_paper.model

import com.google.gson.annotations.SerializedName

data class categorymodel(
    @SerializedName("_id")
    val categoryId: String? = null,

    val name: String = "",
    val description: String = "",
    val createdAt: String? = null,
    val updatedAt: String? = null
)
