package com.example.intellireview_research_paper.data.remote


import com.example.intellireview_research_paper.model.reviewmodel
import retrofit2.Response
import retrofit2.http.*

interface ReviewApi {

    @GET("/getreviews")
    suspend fun getReviews(): Response<List<reviewmodel>>

    @FormUrlEncoded
    @POST("/createreviews")
    suspend fun createReview(
        @Field("paperId") paperId: String,
        @Field("userId") userId: String,
        @Field("rating") rating: Int,
        @Field("comment") comment: String
    ): Response<reviewmodel>

    @FormUrlEncoded
    @PUT("/editreviews/{id}")
    suspend fun editReview(
        @Path("id") reviewId: String,
        @Field("title") title: String,
        @Field("message") message: String
    ): Response<reviewmodel>
}
