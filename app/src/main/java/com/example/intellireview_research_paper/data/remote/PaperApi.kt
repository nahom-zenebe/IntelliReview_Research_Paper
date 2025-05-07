package com.example.intellireview_research_paper.data.remote

import com.example.intellireview_research_paper.model.papermodel
import retrofit2.Response
import retrofit2.http.*

interface PaperApi {

    @GET("/papers")
    suspend fun getPaper(): Response<List<papermodel>>

    @FormUrlEncoded
    @POST("/papers")
    suspend fun createPaper(
        @Field("title") title: String,
        @Field("authors") authors: String,
        @Field("year") year: Int,
        @Field("pdfUrl") pdfUrl: String,
        @Field("uploadedBy") uploadedBy: String,
        @Field("category") category: String,
        @Field("averageRating") averageRating: Double,
        @Field("reviewCount") reviewCount: Int
    ): Response<papermodel>

    @FormUrlEncoded
    @POST("/papers/search")
    suspend fun searchPaper(
        @Field("inputdata") inputData: String
    ): Response<List<papermodel>>

    @DELETE("/papers/{paperId}")
    suspend fun deletePaper(
        @Path("paperId") paperId: String
    ): Response<Void>

    @FormUrlEncoded
    @PUT("/papers/{paperId}")
    suspend fun editPaper(
        @Path("paperId") paperId: String,
        @Field("title") title: String,
        @Field("authors") authors: String,
        @Field("year") year: Int,
        @Field("pdfUrl") pdfUrl: String,
        @Field("uploadedBy") uploadedBy: String,
        @Field("category") category: String,
        @Field("averageRating") averageRating: Double,
        @Field("reviewCount") reviewCount: Int
    ): Response<papermodel>
}

