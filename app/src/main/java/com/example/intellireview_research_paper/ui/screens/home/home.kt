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
import com.example.intellireview_research_paper.ui.components.DrawerContent
import com.example.intellireview_research_paper.ui.components.HomeTopBar
import com.example.intellireview_research_paper.ui.components.ResearchPaperCard
import com.example.intellireview_research_paper.ui.components.SearchBar
import com.example.intellireview_research_paper.ui.viewmodel.BookmarkViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedSort by remember { mutableStateOf("Name") }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val bookmarkViewModel: BookmarkViewModel = viewModel()
    val bookmarkedPapers = bookmarkViewModel.bookmarkedPapers.value
    val bookmarkedPaperIds = bookmarkedPapers.mapNotNull { it.paperId }

    val repo = remember { PaperRepositoryImpl() }

    val papersState = produceState<List<paperModel>?>(initialValue = null) {
        value = try {
            repo.getPapers()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                onLogout = { /* handle logout */ }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    onMenuClick = { coroutineScope.launch { drawerState.open() } },
                    inputname = "IntelliReview"
                )
            },
            bottomBar = { /* bottom nav handled in MainActivity */ }
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
                    null -> {
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> {
                        val filteredPapers = papers.filter { paper ->
                            paper.title?.contains(searchQuery, ignoreCase = true) == true
                        }

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = filteredPapers,
                                key = { it.paperId ?: it.hashCode().toString() }
                            ) { paper ->
                                val context = LocalContext.current

                                Column {
                                    ResearchPaperCard(
                                        title = paper.title.orEmpty(),
                                        imageRes = R.drawable.research_paper,
                                        rating = paper.averageRating ?: 0.0,
                                        pdfUrl = paper.pdfUrl.orEmpty(),onReadClick = {
                                            val pdfUrl = paper.pdfUrl.orEmpty()

                                            // Log the PDF URL to verify it's correct
                                            Log.d("PDF_URL", "PDF URL: $pdfUrl")

                                            // Check if the PDF URL is valid
                                            if (pdfUrl.isNotBlank()) {
                                                val pdfUri = Uri.parse(pdfUrl)
                                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                                    setDataAndType(pdfUri, "application/pdf")
                                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                                }

                                                // Try to open the PDF
                                                try {
                                                    context.startActivity(intent)
                                                } catch (e: ActivityNotFoundException) {
                                                    // Handle the case where no PDF viewer is installed
                                                    Toast.makeText(context, "No PDF viewer app installed", Toast.LENGTH_SHORT).show()
                                                } catch (e: Exception) {
                                                    // Handle any other errors when opening the PDF
                                                    Toast.makeText(context, "Error opening PDF: ${e.message}", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                // Handle invalid or empty URL
                                                Toast.makeText(context, "Invalid PDF URL", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                    )


                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
