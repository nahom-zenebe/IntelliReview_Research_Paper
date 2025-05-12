package com.example.intellireview_research_paper.ui.components

import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext


@Composable
fun ResearchPaperCard(
    title: String,
    imageRes: Int,
    rating: Double,
    pdfUrl: String,
    onReadClick: () -> Unit,
    publishedDate: String = "12/05/2025",
    authorName: String = "john Bereket"
) {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF5D5CBB)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Preview Image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Published: $publishedDate",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
            Text(
                text = "Author: $authorName",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onReadClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(18.dp),
                    contentPadding = PaddingValues(horizontal = 23.dp, vertical = 0.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Read", color = Color(0xFF5D5CBB), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color.Yellow,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = rating.toString(), color = Color.White)

                Spacer(modifier = Modifier.weight(1f))

                // Bookmark Icon
                IconButton(
                    onClick = { /* Handle bookmark action */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Bookmark",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Download Icon - Toast for Downloading & Downloaded
                IconButton(
                    onClick = {
                        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
                        // Simulate download completion after a delay
                        android.os.Handler().postDelayed({
                            Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()
                        }, 2000) // Simulated delay
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FileDownload,
                        contentDescription = "Download",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Comment Icon
                IconButton(
                    onClick = { /* Handle comment action */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Share Icon - Toast for Link Copied
                IconButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        android.content.ClipData.newPlainText("Research Paper Link", pdfUrl).apply {
                            clipboard.setPrimaryClip(this)
                        }
                        Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ResearchPaperCardNorating(
    title: String,
    imageRes: Int,
    publishedDate: String = "Placeholder Date",
    authorName: String = "Placeholder Author"
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF5D5CBB)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Preview Image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Published: $publishedDate",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
            Text(
                text = "Author: $authorName",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* Handle read */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(18.dp),
                    contentPadding = PaddingValues(horizontal = 23.dp, vertical = 0.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Read", color = Color(0xFF5D5CBB), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Spacer(modifier = Modifier.weight(1f))

                // Download Icon - Toast for Downloading & Downloaded
                IconButton(
                    onClick = {
//                        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
//
//                        android.os.Handler().postDelayed({
//                            Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()
//                        }, 2000) // Simulated delay
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FileDownload,
                        contentDescription = "Download",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Comment Icon
                IconButton(
                    onClick = { /* Handle comment action */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Share Icon - Toast for Link Copied
                IconButton(
                    onClick = {
//                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                        android.content.ClipData.newPlainText("Research Paper Link", pdfUrl).apply {
//                            clipboard.setPrimaryClip(this)
//                        }
//                        Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
