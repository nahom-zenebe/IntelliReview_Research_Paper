package com.example.intellireview_research_paper.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellireview_research_paper.data.mapper.UserRepositoryImpl
import com.example.intellireview_research_paper.model.usermodel
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File


class UserViewModel(private val userRepository: UserRepositoryImpl) : ViewModel() {
    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl

    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status
    var user by mutableStateOf<usermodel?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    fun signup(name: String, email: String, password: String, country: String, role: String) {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                user = userRepository.Signup(name, email, password, country, role)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }


    fun login(email: String, password: String) {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                user = userRepository.Login(email, password)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }


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


    fun updateProfile(name: String, bio: String) {
        // Handle DB update or API call here
        _status.value = "Profile updated for $name"
    }

    fun clearStatus() {
        _status.value = null
    }



    }
