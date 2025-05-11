package com.example.intellireview_research_paper.ui.screens.grid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.intellireview_research_paper.ui.components.HomeTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                onMenuClick = { /* Handle menu click */ },
                inputname = "Grid View"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Grid content will be added here
            Text("Grid View Content")
        }
    }
} 