@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.intellireview_research_paper.ui.screens
import CategoryViewCard
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intellireview_research_paper.ui.viewmodel.BookmarkViewModel
import androidx.navigation.NavController
import com.example.intellireview_research_paper.data.mapper.CategoryRepository
import com.example.intellireview_research_paper.data.repository.PaperRepositoryImpl
import com.example.intellireview_research_paper.model.categorymodel
import com.example.intellireview_research_paper.ui.components.DrawerContent
import com.example.intellireview_research_paper.ui.components.HomeTopBar
import com.example.intellireview_research_paper.ui.components.SearchBar
import com.example.intellireview_research_paper.ui.screens.category.CategoryViewModelFactory
import com.example.intellireview_research_paper.viewmodel.CategoryUiState
import com.example.intellireview_research_paper.viewmodel.CategoryViewModel
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.intellireview_research_paper.ui.navigation.Screen

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
fun CategoryEditDialog(
    category: categorymodel,
    onDismiss: () -> Unit,
    onUpdate: (String, String) -> Unit
) {
    var updatedName by remember { mutableStateOf(category.name) }
    var updatedDescription by remember { mutableStateOf(category.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Category") },
        text = {
            Column {
                OutlinedTextField(
                    value = updatedName,
                    onValueChange = { updatedName = it },
                    label = { Text("Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = updatedDescription,
                    onValueChange = { updatedDescription = it },
                    label = { Text("Description") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onUpdate(updatedName, updatedDescription)
                    onDismiss()
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryView(
    navController: NavController,
    repository: CategoryRepository
) {
    // Search and filter state
    var searchQuery by remember { mutableStateOf("") }
    val categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModelFactory(repository))
    val uiState by categoryViewModel.uiState.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var categoryToEdit by remember { mutableStateOf<categorymodel?>(null) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var categoryToDelete by remember { mutableStateOf<categorymodel?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        categoryViewModel.fetchCategories()
    }

    // Drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val bookmarkViewModel: BookmarkViewModel = viewModel()
    val bookmarkedPapers = bookmarkViewModel.bookmarkedPapers.value
    val bookmarkedPaperIds = bookmarkedPapers.mapNotNull { it.paperId }


    if (showEditDialog && categoryToEdit != null) {
        CategoryEditDialog(
            category = categoryToEdit!!,
            onDismiss = {
                showEditDialog = false
                categoryToEdit = null
            },
            onUpdate = { updatedName, updatedDescription ->
                categoryToEdit?.categoryId?.let { id ->
                    categoryViewModel.editCategory(
                        categoryId = id,
                        name = updatedName,
                        description = updatedDescription
                    )
                } ?: run {
                    Toast.makeText(context, "Invalid category ID", Toast.LENGTH_SHORT).show()
                }})

    }

    // Handle delete confirmation
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Deletion") },
            text = {
                Text("Are you sure you want to delete '${categoryToDelete?.name}'?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        categoryToDelete?.categoryId?.let { id ->
                            categoryViewModel.deleteCategory(id)
                            Toast.makeText(context, "Category deleted successfully", Toast.LENGTH_SHORT).show()
                        } ?: run {
                            Toast.makeText(context, "Invalid category ID", Toast.LENGTH_SHORT).show()
                        }

                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        categoryToDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    LaunchedEffect(uiState) {
        if (uiState is CategoryUiState.Success) {
            Toast.makeText(context, "Category updated", Toast.LENGTH_SHORT).show()
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
                    inputname = "IntelliReview",
                    onNotificationClick = {
                        // Navigate to notification screen
                        navController.navigate(Screen.CreateNotification.route)
                    }
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
                    onQueryChanged = { newQuery ->
                        searchQuery = newQuery
                        categoryViewModel.searchCategory(newQuery)
                    }
                )

                Spacer(Modifier.height(12.dp))

                when (val state = uiState) {
                    is CategoryUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is CategoryUiState.Success -> {
                        if (state.categories.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No categories found")
                            }
                        } else {
                            LazyColumn {
                                items(state.categories) { category ->
                                    CategoryViewCard(
                                        text = category.name,
                                        onEditClick = {

                                            categoryToEdit = category
                                            showEditDialog = true
                                        },
                                        onDeleteClick = {
                                            categoryToDelete = category
                                            showDeleteDialog = true
                                        }
                                    )
                                }
                            }
                        }
                    }
                    is CategoryUiState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = state.message,
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { categoryViewModel.fetchCategories() }
                                ) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                    CategoryUiState.Idle -> {
                        // Initial state, could show empty state or loading
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}