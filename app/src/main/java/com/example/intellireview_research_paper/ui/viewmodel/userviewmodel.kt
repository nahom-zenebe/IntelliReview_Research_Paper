import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellireview_research_paper.data.mapper.UserRepositoryImpl
import com.example.intellireview_research_paper.model.usermodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl

    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status
    var user by mutableStateOf<usermodel?>(null)
        private set

    private val _user = MutableStateFlow<usermodel?>(null)
    val userInfo: StateFlow<usermodel?> get() = _user


    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Signup function
    fun signup(name: String, email: String, password: String, country: String, role: String) {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                println("Attempting signup with: $name, $email, $password, $country, $role")

                // Create the JSON object manually
                val jsonObject = JSONObject().apply {
                    put("name", name)
                    put("email", email)
                    put("password", password)
                    put("country", country)
                    put("role", role)
                }

                // Convert JSON object to string and then to RequestBody
                val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())

                // Call the repository's signup function with the JSON requestBody
                user = userRepository.Signup(
                    name = name,
                    email = email,
                    password = password,
                    country = country,
                    role = role
                )
                _user.value = user
                println("Signup response: $user")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                println("Signup error: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }

    // Login function
    fun login(email: String, password: String) {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                // Create a JSON object for login
                val jsonObject = JSONObject().apply {
                    put("email", email)
                    put("password", password)
                }

                val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())

                // Call the repository's login function
                user = userRepository.Login(
                    email = email,
                    password = password
                )
                _user.value = user

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // Logout function
    fun logout() {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                userRepository.Logout()
                user = null // Reset user data on logout
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // Upload image function to Cloudinary
    fun uploadImageToCloudinary(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val cloudinaryUrl = "https://api.cloudinary.com/v1_1/<your-cloud-name>/image/upload"
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.name, file.asRequestBody("image/*".toMediaType()))
                .addFormDataPart("upload_preset", "<your-upload-preset>")
                .build()

            val request = Request.Builder()
                .url(cloudinaryUrl)
                .post(requestBody)
                .build()

            val client = OkHttpClient()
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val json = JSONObject(response.body?.string() ?: "")
                    val imageUrl = json.getString("secure_url")
                    _profileImageUrl.value = imageUrl
                    _status.value = "Upload Successful"
                } else {
                    _status.value = "Upload Failed: ${response.message}"
                }
            } catch (e: Exception) {
                _status.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    // Update profile function
    fun updateProfile(name: String, bio: String) {
        // Handle DB update or API call here
        _status.value = "Profile updated for $name"
    }

    // Clear status
    fun clearStatus() {
        _status.value = null
    }
}
