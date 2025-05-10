package com.example.intellireview_research_paper.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Call `"foo".toPlainRequestBody()` to get a text/plain RequestBody
 */
fun String.toPlainRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())
