package com.example.intellireview_research_paper.api

import com.example.intellireview_research_paper.model.paperModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PaperApi {
    @Multipart
    @POST("upload")
    suspend fun createPaper(
        @Part("title")      title:      RequestBody,
        @Part("authors")    authors:    RequestBody,
        @Part("year")       year:       RequestBody,
        @Part("uploadedBy") uploadedBy: RequestBody,
        @Part("category")   category:   RequestBody,
        @Part               pdf:        MultipartBody.Part
    ): Response<paperModel>

    @Multipart
    @PUT("update/{id}")
    suspend fun updatePaper(
        @Path("id")         paperId:    String,
        @Part("title")      title:      RequestBody,
        @Part("authors")    authors:    RequestBody,
        @Part("year")       year:       RequestBody,
        @Part("uploadedBy") uploadedBy: RequestBody,
        @Part("category")   category:   RequestBody,
        @Part               pdf:        MultipartBody.Part?
    ): Response<paperModel>

    @GET("viewPapers")
    suspend fun getPapers(): Response<List<paperModel>>

    @GET("searchpapers")
    suspend fun searchPaper(@Query("inputdata") inputData: String): Response<List<paperModel>>

    @DELETE("delete/{id}")
    suspend fun deletePaper(@Path("id") paperId: String): Response<Unit>
}
