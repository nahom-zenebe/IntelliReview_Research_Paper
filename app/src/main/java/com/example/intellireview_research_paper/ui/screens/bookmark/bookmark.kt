// ui/screens/BookmarkScreen.kt

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.intellireview_research_paper.ui.screens

import FilterSortRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intellireview_research_paper.model.paperModel
import com.example.intellireview_research_paper.ui.components.BookmarkCard
import com.example.intellireview_research_paper.ui.components.DrawerContent
import com.example.intellireview_research_paper.ui.components.HomeTopBar
import com.example.intellireview_research_paper.ui.components.SearchBar
import com.example.intellireview_research_paper.ui.viewmodel.BookmarkViewModel
import kotlinx.coroutines.launch

// ─── Add these two imports ────────────────────────────────────────────────────
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
// ──────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navController: NavController,
    onLogout: () -> Unit = {}
) {
    val bookmarkViewModel: BookmarkViewModel = viewModel()

    // 1) Collect the StateFlow
    val allBookmarked by bookmarkViewModel.bookmarkedPapers.collectAsState()

    // 2) Local UI state
    var query by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedSort by remember { mutableStateOf("Name") }

    // 3) Drawer
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                onLogout = {
                    scope.launch { drawerState.close() }
                    onLogout()
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    inputname = "Bookmarks"
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Search
                SearchBar(query = query, onQueryChanged = { query = it })
                Spacer(Modifier.height(12.dp))

                // Filter & Sort controls
                FilterSortRow(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it },
                    selectedSort = selectedSort,
                    onSortSelected = { selectedSort = it }
                )
                Spacer(Modifier.height(16.dp))

                // Apply search / filter / sort
                val displayed = remember(allBookmarked, query, selectedFilter, selectedSort) {
                    allBookmarked
                        // search by title
                        .filter { it.title.orEmpty().contains(query, ignoreCase = true) }
                        // filter by category if not “All”
                        .let { list ->
                            if (selectedFilter == "All") list
                            else list.filter { it.category == selectedFilter }
                        }
                        // sort
                        .let { list ->
                            when (selectedSort) {
                                "Name" -> list.sortedBy { it.title.orEmpty() }
                                "Date" -> list.sortedByDescending { it.createdAt }
                                else -> list
                            }
                        }
                }

                // Empty or list
                if (displayed.isEmpty()) {
                    EmptyBookmarksState()
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(displayed, key = { it.paperId!! }) { paper ->
                            BookmarkCard(
                                paper = paper,
                                isBookmarked = true,
                                onBookmarkClick = { bookmarkViewModel.toggleBookmark(paper) },
                                onClick = {
                                    navController.navigate("paperDetail/${paper.paperId}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyBookmarksState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No bookmarks yet",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}
