import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminViewModel : ViewModel() {
    private val _adminStats = mutableStateOf(AdminStats(0, 0))
    val adminStats: State<AdminStats> = _adminStats

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val apiService = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3500/api/") // For Android emulator to access localhost
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AdminApiService::class.java)

    fun fetchAdminStats() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getAdminStats()
                if (response.isSuccessful && response.body() != null) {
                    _adminStats.value = response.body()!!.stats
                } else {
                    Log.e("AdminViewModel", "Failed response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("AdminViewModel", "Error fetching stats", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
