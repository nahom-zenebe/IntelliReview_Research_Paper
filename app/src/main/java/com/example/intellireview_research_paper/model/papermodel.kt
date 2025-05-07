package com.example.intellireview_research_paper.model

data class papermodel (
    val paperId:String?=null,
    val title:String?=null,
    val authors:String?=null,
    val year:Number=0,
    val  pdfUrl:String?=null,
    val uploadedBy:String?=null,
    val category:String?=null,
    val averageRating:Double=0.0,
    val  reviewCount:Number=0,
    val createdAt: String? = null,
    val updatedAt: String? = null


)