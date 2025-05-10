import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.components.BookmarkCard

import com.example.intellireview_research_paper.ui.components.DrawerContent
import com.example.intellireview_research_paper.ui.components.FilterSortCategory
import com.example.intellireview_research_paper.ui.components.HomeTopBar
import com.example.intellireview_research_paper.ui.components.SearchBar
import java.nio.file.WatchEvent

@Composable
fun AdminDashBoard(
    onMenuClick: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var query by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Scaffold(
            topBar = {

                HomeTopBar(
                    onMenuClick = onMenuClick,
                    inputname = "Admin Dashboard"
                )


            }
        )

        { innerPadding ->

            Column(modifier = Modifier.padding(innerPadding)) {
                SearchBar(query, onQueryChanged = { query = it })
                FilterSortCategory (
                    selectedFilter = selectedFilter,
                    onFilterSelected = { newFilter ->
                        selectedFilter = newFilter
                    },
                    selectedSort = selectedSort,
                    onSortSelected = { newSort ->
                        selectedSort = newSort
                    },
                    selectedCategory = selectedCategory,
                    onCategorySelected = { newCategory ->
                        selectedCategory = newCategory
                    }
                )
                Spacer(Modifier.height(30.dp))



            }
        }


    }


}
@Preview(showBackground = true)
        @Composable
        fun AdminDashBoardPreview() {
            MaterialTheme {
                AdminDashBoard()

            }
        }