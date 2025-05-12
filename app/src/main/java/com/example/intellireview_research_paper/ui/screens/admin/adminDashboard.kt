import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.components.FilterSortCategory
import com.example.intellireview_research_paper.ui.components.HomeTopBar
import com.example.intellireview_research_paper.ui.components.ResearchPaperCard
import com.example.intellireview_research_paper.ui.components.SearchBar
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.intellireview_research_paper.ui.components.DrawerContent
import kotlinx.coroutines.launch
import com.example.intellireview_research_paper.ui.navigation.Screen

@Composable
fun AdminDashboard(
    navController: NavController,
    onLogout: () -> Unit = {}
) {
    val viewModel: AdminViewModel = viewModel()
    val stats by viewModel.adminStats
    val isLoading by viewModel.isLoading

    // Search and filter states
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchAdminStats()
    }

    // Drawer logic
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                onLogout = {
                    coroutineScope.launch { drawerState.close() }
                    onLogout()
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    onMenuClick = { coroutineScope.launch { drawerState.open() } },
                    inputname = "Admin Dashboard",
                    onNotificationClick =  { navController.navigate(Screen.CreateNotification.route)}
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .padding(innerPadding)
            ) {
                // Search and filters
                SearchBar(query = query, onQueryChanged = { query = it })

                FilterSortCategory(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it },
                    selectedSort = selectedSort,
                    onSortSelected = { selectedSort = it },
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Stats cards
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Column {
                        StatsRow(stats = stats)

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Recently posted",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )


                        // Demo 1: With rating
                        ResearchPaperCard(
                            title = "Blockchain Applications in Research",
                            imageRes = R.drawable.research_paper, // Replace with actual drawable
                            rating = 4.0, // Set a default or actual rating value
                            pdfUrl = "https://path.to/your/pdf", // Replace with the actual PDF URL
                            onReadClick = {
                                // Handle read click, e.g., open PDF viewer
                            },
                            publishedDate = "2025-01-20",
                            authorName = "Prof. Sara Mekonnen",
                            isBookmarked = false,
                            onBookmarkClick = {}
                        )

                        ResearchPaperCard(
                            title = "Blockchain Applications in Research",
                            imageRes = R.drawable.research_paper, // Replace with actual drawable
                            rating = 4.0, // Set a default or actual rating value
                            pdfUrl = "https://path.to/your/pdf", // Replace with the actual PDF URL
                            onReadClick = {
                                // Handle read click, e.g., open PDF viewer
                            },
                            publishedDate = "2025-01-20",
                            authorName = "Prof. Sara Mekonnen",
                            isBookmarked = false,
                            onBookmarkClick = {}
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun StatsRow(stats: AdminStats) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatCard(
            count = stats.users.toString(),
            title = "Users",
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(16.dp))
        StatCard(
            count = stats.papers.toString(),
            title = "Researches",
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(16.dp))
        StatCard(
            count = "0",
            title = "Comments",
            modifier = Modifier.weight(1f)

        )
    }
}

@Composable
fun StatCard(
    count: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminDashboardPreview() {
    MaterialTheme {
        AdminDashboard(navController = rememberNavController())
    }
}

