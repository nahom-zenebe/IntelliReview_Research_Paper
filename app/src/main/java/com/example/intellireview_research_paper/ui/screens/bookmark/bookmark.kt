@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.intellireview_research_paper.ui.screens

import FilterSortRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intellireview_research_paper.model.paperModel
import com.example.intellireview_research_paper.ui.components.*
import com.example.intellireview_research_paper.ui.viewmodel.BookmarkViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navController: NavController,
    onLogout: () -> Unit = {}
) {
    val viewModel: BookmarkViewModel = viewModel()
    val bookmarkedPapers by viewModel.bookmarkedPapers

    // Search & filter state
    var query by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf<String?>(null) }

    // Drawer state
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
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                SearchBar(
                    query = query,
                    onQueryChanged = { query = it }
                )

                Spacer(Modifier.height(12.dp))

                FilterSortRow(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it },
                    selectedSort = selectedSort,
                    onSortSelected = { selectedSort = it }
                )

                Spacer(Modifier.height(16.dp))

                when {
                    bookmarkedPapers.isEmpty() -> {
                        EmptyBookmarksState()
                    }
                    else -> {
                        BookmarkList(
                            papers = bookmarkedPapers,
                            onBookmarkToggle = { paper -> viewModel.toggleBookmark(paper) },
                            onPaperClick = { paper ->
                                navController.navigate("paperDetail/${paper.paperId}")
                            }
                        )
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

@Composable
private fun BookmarkList(
    papers: List<paperModel>,
    onBookmarkToggle: (paperModel) -> Unit,
    onPaperClick: (paperModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(papers) { paper ->
            BookmarkCard(
                paper = paper,
                isBookmarked = true,
                onBookmarkClick = { onBookmarkToggle(paper) },
                onClick = { onPaperClick(paper) }
            )
        }
    }
}
