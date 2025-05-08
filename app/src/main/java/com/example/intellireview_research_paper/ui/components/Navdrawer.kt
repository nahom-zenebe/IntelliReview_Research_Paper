package com.example.intellireview_research_paper.ui.components

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.theme.Purple40
import com.example.intellireview_research_paper.ui.theme.subtleGray
@Composable
fun ClasticRelieveApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(subtleGray)

    ) {
        // Header with logo and email
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment =  Alignment.Start
        ) {
            Column (
                modifier = Modifier.fillMaxWidth().background(Purple40).padding(0.dp).offset(x = 30.dp)
            ){
            // Assuming you have a logo image in your resources


            Image(
                painter = painterResource(id = R.drawable.profile), // Replace with your actual logo
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)


            )

            Spacer(modifier = Modifier.width(76.dp))


                Text(
                    text = "Clastic_Relieve",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Clasticyedservices@gmail.com",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )}

        }

        Spacer(modifier = Modifier.height(32.dp))

        // Navigation items
        NavigationItem(icon = Icons.Default.Home, text = "Home")
        NavigationItem(icon = Icons.Default.Person, text = "Profile")
        NavigationItem(icon = Icons.Default.Bookmark, text = "Bookmark")
        NavigationItem(icon = Icons.Default.Create, text = "create category")
        NavigationItem(icon = Icons.Default.ViewList, text = "view category")

        Spacer(modifier = Modifier.weight(1f))

        // Logout button at the bottom
        NavigationItem(icon = Icons.Default.Logout, text = "Log Out")
    }
}

@Composable
fun NavigationItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClasticRelieveApp() {
    ClasticRelieveApp()
}