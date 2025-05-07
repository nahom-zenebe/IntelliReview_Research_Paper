package com.example.intellireview_research_paper.data.mapper

import com.example.intellireview_research_paper.model.categorymodel


interface CategoryRepository {
    suspend fun getCategory():List<categorymodel>
    suspend fun createCategory(name: String,description: String):categorymodel
    suspend fun searchCategory(inputdata:String)
    suspend fun deleteCategory(categoryId:String)
    suspend fun EditCategory(categoryId:String,name: String,description: String):categorymodel

}