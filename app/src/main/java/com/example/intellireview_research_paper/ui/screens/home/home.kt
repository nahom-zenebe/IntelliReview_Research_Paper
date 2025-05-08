// HomeScreen.kt
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.intellireview_research_paper.ui.screens

import FilterSortRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.components.ResearchPaperCard
import com.example.intellireview_research_paper.ui.components.SearchBar

@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedSort by remember { mutableStateOf("Name") }

    val researchPapers = listOf(
        Triple("AI in Healthcare", R.drawable.research_paper, 4.5),
        Triple("Blockchain Security", R.drawable.research_paper, 4.2),
        Triple("Quantum Computing 101", R.drawable.research_paper, 4.7)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore Research") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5D5CBB),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            // TODO: add BottomNavBar here
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChanged = { newText -> searchQuery = newText }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilterSortRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it },
                selectedSort = selectedSort,
                onSortSelected = { selectedSort = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(researchPapers) { (title, imageRes, rating) ->
                    ResearchPaperCard(
                        title = title,
                        imageRes = imageRes,
                        rating = rating
                    )
                }
            }
        }
    }
}
