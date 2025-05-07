package com.example.intellireview_research_paper.data.remote

import com.example.intellireview_research_paper.model.papermodel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

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



object PaperApiClient {

    private const val BASE_URL = "https://localhost:3500/api"  // Update with your actual base URL

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())  // Optional: You can add an OkHttpClient for SSL or logging
        .addConverterFactory(GsonConverterFactory.create())  // Gson converter for handling JSON responses
        .build()

    val apiService: PaperApi = retrofit.create(PaperApi::class.java)

    // Optional: Add custom OkHttpClient (e.g., for logging, SSL handling, etc.)
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Set timeouts
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
