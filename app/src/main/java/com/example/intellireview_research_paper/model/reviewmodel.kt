package com.example.intellireview_research_paper.model

data class reviewmodel (
    val paperId:String?=null,
    val userId: String? = null,
    val rating: Number?= 1,
    val comment:String?=null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)