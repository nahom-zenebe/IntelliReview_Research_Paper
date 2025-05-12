// File: ui/screens/UserProfileScreen.kt
package com.example.intellireview_research_paper.ui.screens

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intellireview_research_paper.R

data class Paper(
    val title: String,
    val averageRating: Double
)

@Composable
fun UserProfileScreen() {
    val context = LocalContext.current

    // Read saved user info from SharedPreferences each time
    val prefs = remember {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
    val name  = prefs.getString("KEY_NAME", "")  ?: ""
    val email = prefs.getString("KEY_EMAIL", "") ?: ""
    val role  = prefs.getString("KEY_ROLE", "")  ?: ""

    Log.d("UserProfileScreen", "Name: $name, Email: $email, Role: $role")

    // Launcher stub (for future upload)
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // val file = FileUtils.getFileFromUri(context, it)
            // file?.let { viewModel.uploadImageToCloudinary(it) }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5D5CBB))
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Profile picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = name.ifBlank { "Guest User" },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = email.ifBlank { "No email" },
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = role.ifBlank { "User" }.replaceFirstChar { it.uppercase() },
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(Icons.Default.Star,   "0.0", "Avg rates")
                        StatItem(Icons.Default.Comment,"0", "Comments")
                        StatItem(Icons.Default.PostAdd,"0", "Posts")
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Recent posts",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(Modifier.height(32.dp))

                // Centered message
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No recent posts",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color(0xFFFFA500), modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
private fun ResearchPaperCard(
    title: String,
    imageRes: Int,
    rating: Double
) {
    Surface(
        color = Color(0xFF5D5CBB),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Research Paper Thumbnail",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.White
                    ) {
                        Text(
                            text = "Rating: $rating",
                            color = Color(0xFF5D5CBB),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Icon(Icons.Default.Download, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Icon(Icons.Default.BookmarkBorder, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}
