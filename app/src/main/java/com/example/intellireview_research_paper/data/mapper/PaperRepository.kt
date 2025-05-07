package com.example.intellireview_research_paper.data.mapper

import com.example.intellireview_research_paper.model.papermodel


interface PaperRepository  {
    suspend fun getPaper():List<papermodel>
    suspend fun createPaper(title: String,authors: String,year:Number,pdfUrl:String,uploadedBy:String,category:String,averageRating:Double, reviewCount:Number): papermodel
    suspend fun searchPaper(inputdata:String):List<papermodel>
    suspend fun deletePaper(paperId:String)
    suspend fun EditPaper(paperId:String,title: String,authors: String,year:Number,pdfUrl:String,uploadedBy:String,category:String,averageRating:Double, reviewCount:Number):papermodel

}


