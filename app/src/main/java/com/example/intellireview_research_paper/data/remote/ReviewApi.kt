package com.example.intellireview_research_paper.data.remote


import com.example.intellireview_research_paper.model.reviewmodel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


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


object ReviewApiClient {

    private const val BASE_URL = "https://localhost:3500/api"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ReviewApi = retrofit.create(ReviewApi::class.java)


    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
