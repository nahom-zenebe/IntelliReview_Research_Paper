import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intellireview_research_paper.MainScreen
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.components.BookmarkCard
import com.example.intellireview_research_paper.ui.components.HomeTopBar
import com.example.intellireview_research_paper.ui.components.SearchBar
import com.example.intellireview_research_paper.ui.theme.IntelliReview_Research_PaperTheme
import com.example.intellireview_research_paper.model.paperModel
import com.example.intellireview_research_paper.ui.viewmodel.BookmarkViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intellireview_research_paper.ui.components.*
import com.example.intellireview_research_paper.ui.theme.IntelliReview_Research_PaperTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    onMenuClick: () -> Unit,
    navController: NavController? = null // Make optional for preview
) {
    val viewModel: BookmarkViewModel = viewModel()
    val bookmarkedPapers by viewModel.bookmarkedPapers

    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf<String?>(null) }
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    HomeTopBar(
                        onMenuClick = onMenuClick,
                        inputname = "Bookmarks"
                    )
                }
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
                            navController?.navigate("paperDetail/${paper.paperId}")
                        }
                    )
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



@Preview(showBackground = true)
@Composable
fun BookmarkPreview() {
    IntelliReview_Research_PaperTheme {
        BookmarkScreen(
            onMenuClick = {},

        )
    }
}