@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.intellireview_research_paper.ui.screens
import CategoryViewCard
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import FilterSortRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intellireview_research_paper.ui.viewmodel.BookmarkViewModel
import androidx.navigation.NavController
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.data.mapper.CategoryRepository
import com.example.intellireview_research_paper.data.repository.PaperRepositoryImpl
import com.example.intellireview_research_paper.model.paperModel
import com.example.intellireview_research_paper.ui.components.DrawerContent
import com.example.intellireview_research_paper.ui.components.HomeTopBar
import com.example.intellireview_research_paper.ui.components.ResearchPaperCard
import com.example.intellireview_research_paper.ui.components.SearchBar
import com.example.intellireview_research_paper.ui.screens.category.CategoryViewModelFactory
import com.example.intellireview_research_paper.viewmodel.CategoryUiState
import com.example.intellireview_research_paper.viewmodel.CategoryViewModel
import kotlinx.coroutines.launch


class CategoryViewModelFactory(
    private val repository: CategoryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun CategoryView(
    navController: NavController,
    repository: CategoryRepository
) {
    // Search and filter state
    var searchQuery by remember { mutableStateOf("") }
    val categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModelFactory(repository))
    val uiState by categoryViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        categoryViewModel.fetchCategories()
    }

    // Drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val bookmarkViewModel: BookmarkViewModel = viewModel()
    val bookmarkedPapers = bookmarkViewModel.bookmarkedPapers.value
    val bookmarkedPaperIds = bookmarkedPapers.mapNotNull { it.paperId }

    // Repository
    val repo = remember { PaperRepositoryImpl() }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemSelected = { /* handle drawer clicks */ },
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



                Spacer(Modifier.height(8.dp))

                when (val state = uiState) {
                    is CategoryUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }


                    is CategoryUiState.Success -> {
                        LazyColumn {
                            items(state.categories) { category ->
                                CategoryViewCard(
                                    text = category.name,
                                    onEditClick = {
                                        // TODO: Open edit dialog or screen
                                    },
                                    onDeleteClick = {
                                        categoryViewModel.deleteCategory(category.categoryId.toString())
                                    }
                                )
                            }
                        }
                    }

                    else -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Error: getting category",
                                color = MaterialTheme.colorScheme.error
                            )
                    }
                }
        }}}}}
