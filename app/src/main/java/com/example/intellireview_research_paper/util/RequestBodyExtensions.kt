import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun String.toPlainRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}
