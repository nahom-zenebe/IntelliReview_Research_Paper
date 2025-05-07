package com.example.intellireview_research_paper.data.remote


import com.example.intellireview_research_paper.model.categorymodel
import retrofit2.Response
import retrofit2.http.*

interface CategoryApi {

    @GET("/getcategories")
    suspend fun getCategories(): Response<List<categorymodel>>

    @FormUrlEncoded
    @POST("/createcategories")
    suspend fun createCategory(
        @Field("name") name: String,
        @Field("description") description: String
    ): Response<categorymodel>

    @FormUrlEncoded
    @POST("/searchcategories/search")
    suspend fun searchCategory(
        @Field("inputdata") inputData: String
    ): Response<List<categorymodel>>

    @DELETE("/deletecategories/{id}")
    suspend fun deleteCategory(
        @Path("id") categoryId: String
    ): Response<Void>

    @FormUrlEncoded
    @PUT("/editcategories/{id}")
    suspend fun editCategory(
        @Path("id") categoryId: String,
        @Field("name") name: String,
        @Field("description") description: String
    ): Response<categorymodel>
}
