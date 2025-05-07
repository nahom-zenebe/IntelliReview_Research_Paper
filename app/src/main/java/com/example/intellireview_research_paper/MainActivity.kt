package com.example.intellireview_research_paper

import BottomNavBar
import FilterSortRow
import SearchBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intellireview_research_paper.ui.theme.IntelliReview_Research_PaperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntelliReview_Research_PaperTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    var selectedIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedIndex,
                onItemSelected = { selectedIndex = it },
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // 🔍 Search bar at the top
            SearchBar(
                query = searchQuery,
                onQueryChanged = { searchQuery = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ➡️ Filter & Sort row below search
            FilterSortRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it },
                selectedSort = selectedSort,
                onSortSelected = { selectedSort = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ... your screen content goes here ...
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    IntelliReview_Research_PaperTheme {
        MainScreen()
    }
}
