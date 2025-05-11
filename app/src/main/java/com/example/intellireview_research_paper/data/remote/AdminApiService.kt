import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET

// network/AdminApiService.kt
interface AdminApiService {
    @GET("admin/stats")
    suspend fun getAdminStats(): Response<AdminStatsResponse>
}



data class AdminStatsResponse(
    @SerializedName("stats") val stats: AdminStats
)

data class AdminStats(
    @SerializedName("papers") val papers: Int,
    @SerializedName("users") val users: Int
)