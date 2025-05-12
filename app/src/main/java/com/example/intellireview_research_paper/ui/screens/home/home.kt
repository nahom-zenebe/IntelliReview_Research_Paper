// ui/screens/HomeScreen.kt

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.intellireview_research_paper.ui.screens

import FilterSortRow
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.data.repository.PaperRepositoryImpl
import com.example.intellireview_research_paper.model.paperModel
import com.example.intellireview_research_paper.ui.components.*
import com.example.intellireview_research_paper.ui.viewmodel.BookmarkViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedSort by remember { mutableStateOf("Name") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // 1. ViewModel + state
    val bookmarkViewModel: BookmarkViewModel = viewModel()
    val bookmarkedPapers by bookmarkViewModel.bookmarkedPapers.collectAsState()
    val bookmarkedIds = bookmarkedPapers.mapNotNull { it.paperId }

    // 2. Load papers once
    val repo = remember { PaperRepositoryImpl() }
    val papersState = produceState<List<paperModel>?>(initialValue = null) {
        value = runCatching { repo.getPapers() }.getOrElse {
            it.printStackTrace()
            emptyList()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                onLogout = { /* TODO */ }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    onMenuClick = { coroutineScope.launch { drawerState.open() } },
                    inputname = "IntelliReview"
                )
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
                    onQueryChanged = { searchQuery = it }
                )

                Spacer(Modifier.height(12.dp))

                FilterSortRow(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it },
                    selectedSort = selectedSort,
                    onSortSelected = { selectedSort = it }
                )

                Spacer(Modifier.height(8.dp))

                when (val papers = papersState.value) {
                    null -> Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                    else -> {
                        val filtered = papers.filter { paper ->
                            paper.title?.contains(searchQuery, ignoreCase = true) == true
                        }

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                filtered,
                                key = { it.paperId ?: it.hashCode().toString() }
                            ) { paper ->
                                ResearchPaperCard(
                                    title = paper.title.orEmpty(),
                                    imageRes = R.drawable.research_paper,
                                    rating = paper.averageRating ?: 0.0,
                                    pdfUrl = paper.pdfUrl.orEmpty(),
                                    isBookmarked = bookmarkedIds.contains(paper.paperId),
                                    onReadClick = {
                                        val url = paper.pdfUrl.orEmpty()
                                        Log.d("PDF_URL", "PDF URL: $url")
                                        if (url.isBlank()) {
                                            Toast.makeText(context, "Invalid PDF URL", Toast.LENGTH_SHORT).show()
                                        } else {
                                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                                data = Uri.parse(url)
                                                setDataAndType(data, "application/pdf")
                                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                            }
                                            runCatching { context.startActivity(intent) }
                                                .onFailure { err ->
                                                    val msg = if (err is ActivityNotFoundException) {
                                                        "No PDF viewer installed"
                                                    } else {
                                                        "Error opening PDF: ${err.message}"
                                                    }
                                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    },
                                    onBookmarkClick = {
                                        bookmarkViewModel.toggleBookmark(paper)
                                    },
                                    publishedDate =  "12/05/2025",
                                    authorName =  "Unknown Author"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
