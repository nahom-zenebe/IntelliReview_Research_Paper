package com.example.intellireview_research_paper.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PaperApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3500/api/paper/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: PaperApi = retrofit.create(PaperApi::class.java)
}