package com.example.intellireview_research_paper.data.remote


import com.example.intellireview_research_paper.data.mapper.CategoryRepository
import com.example.intellireview_research_paper.model.categorymodel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface CategoryApi {

    @GET("category/getalCategory")
    suspend fun getCategories(): Response<List<categorymodel>>

    @FormUrlEncoded
    @POST("category/createCategory")
    suspend fun createCategory(
        @Field("name") name: String,
        @Field("description") description: String
    ): Response<categorymodel>

    @FormUrlEncoded
    @POST("category/searchcategories/search")
    suspend fun searchCategory(
        @Field("inputdata") inputData: String
    ): Response<List<categorymodel>>

    @DELETE("category/deleteCategory/{id}")
    suspend fun deleteCategory(
        @Path("id") categoryId: String
    ): Response<Void>

    @FormUrlEncoded
    @PUT("category/updateCategory/{id}")
    suspend fun editCategory(
        @Path("id") categoryId: String,
        @Field("name") name: String,
        @Field("description") description: String
    ): Response<categorymodel>
}



object CategoryApiClient {

    private const val BASE_URL = "http://10.0.2.2:3500/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: CategoryApi = retrofit.create(CategoryApi::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
