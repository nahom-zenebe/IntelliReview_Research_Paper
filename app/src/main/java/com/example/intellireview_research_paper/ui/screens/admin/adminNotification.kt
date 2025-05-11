import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.example.intellireview_research_paper.data.mapper.CategoryRepository
import com.example.intellireview_research_paper.data.mapper.NotificationRepository
import com.example.intellireview_research_paper.data.repository.NotificationRepositoryImpl
import com.example.intellireview_research_paper.ui.screens.category.CategoryViewModelFactory
import com.example.intellireview_research_paper.ui.theme.White
import com.example.intellireview_research_paper.ui.theme.deepBlue
import com.example.intellireview_research_paper.viewmodel.CategoryViewModel
import com.example.intellireview_research_paper.viewmodel.NotificationViewModel


class NotificationViewModelFactory(
    private val repository: NotificationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




@Composable
fun NotificationScreen(
    navController: NavController,
    repository: NotificationRepository

) {

    val viewModel: NotificationViewModel = viewModel(
        factory = NotificationViewModelFactory(repository)
    )

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current
    LaunchedEffect(viewModel.notificationCreated) {
        viewModel.notificationCreated.collect { notification ->
            Toast.makeText(
                context,
                "Category '${notification.title}' created!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Notification",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp).offset(8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier=Modifier.fillMaxWidth()
                .background(
                    color=deepBlue,shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
                ,



            ) {

                Spacer(modifier = Modifier.height(12.dp))
                // Label TextField (smaller)
                Text(
                    modifier = Modifier.fillMaxWidth().offset(x = 12.dp),
                    text = "Title",
                    fontWeight = FontWeight.Light,
                    fontSize = 23.sp,
                    color = White


                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = title,
                    onValueChange = { title = it },

                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE6E6FA), // Light Lavender
                        unfocusedContainerColor = Color(0xFFE6E6FA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(),

                    )
                Spacer(modifier = Modifier.height(24.dp))
                // Description TextField (larger)
                Text(
                    modifier = Modifier.fillMaxWidth().offset(x = 12.dp),
                    text = " message",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Light,
                    color = White


                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value =  message,
                    onValueChange = {  message = it },

                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE6E6FA),
                        unfocusedContainerColor = Color(0xFFE6E6FA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp), // Wider and taller

                    singleLine = false // Allow multiline input
                )
                Spacer(modifier = Modifier.height(32.dp))
                // Create Button (Purple-Grey)
                Button(
                    onClick = {
                        viewModel.createNotification(title,  message)
                    },
                    modifier = Modifier
                        .width(190.dp)
                        .height(35.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(12.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Create",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    MaterialTheme {

    }
}