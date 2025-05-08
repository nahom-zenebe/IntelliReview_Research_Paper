package com.example.intellireview_research_paper.ui.components

import com.example.intellireview_research_paper.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeTopBar(
    onMenuClick: () -> Unit,
    inputname:String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFF36454F),
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$inputname",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF36454F)
            )
        }

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            tint = Color(0xFF36454F),
            modifier = Modifier.size(28.dp)


        )
    }
}



@Composable
fun DrawerContent(
    onItemSelected: (String) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
    ) {

        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5D5CBB))
                .height(180.dp)
                .padding(top = 24.dp, start = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_screen_container),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "John Doe",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
            Text(
                text = "john.doe@example.com",
                fontSize = 14.sp,
                color = Color.White
            )
        }





        // Drawer body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8E8ED))
                .padding(start = 16.dp, top = 24.dp, bottom = 24.dp)
        ) {
            val items = listOf(
                "Home" to Icons.Outlined.Home,
                "Profile" to Icons.Outlined.Person,
                "Bookmark" to Icons.Outlined.BookmarkBorder,
                "Create Category" to Icons.Outlined.GridView,
                "View Category" to Icons.Outlined.GridView
            )

            items.forEach { (label, icon) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemSelected(label) }
                        .padding(vertical = 16.dp)

                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = Color(0xFF36454F),
                        modifier = Modifier.size(23.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = label,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF36454F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(250.dp))

            HorizontalDivider(modifier = Modifier.padding(end = 16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLogout() }
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = "Logout",
                    tint = Color(0xFF36454F),
                    modifier = Modifier.size(23.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Logout",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF36454F)
                )
            }
        }
    }
}




