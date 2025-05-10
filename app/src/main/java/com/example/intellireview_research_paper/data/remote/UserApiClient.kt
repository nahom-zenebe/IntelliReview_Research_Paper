// in your UserApiClient.kt

import com.example.intellireview_research_paper.data.remote.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object UserApiClient {
    private const val BASE_URL = "http://10.0.2.2:3500/api/"

    private fun createOkHttpClient(): OkHttpClient {
        // 1) Create the interceptor and set it to log the full body
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)          // ← add it here
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: UserApi = retrofit.create(UserApi::class.java)
}
