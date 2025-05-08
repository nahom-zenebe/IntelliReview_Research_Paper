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
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.painterResource
import com.example.intellireview_research_paper.MainScreen
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.components.BookmarkCard
import com.example.intellireview_research_paper.ui.components.HomeTopBar
import com.example.intellireview_research_paper.ui.components.SearchBar
import com.example.intellireview_research_paper.ui.theme.IntelliReview_Research_PaperTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bookmark( onMenuClick: () -> Unit) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf<String?>(null) }
    var query  by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(

                { HomeTopBar(onMenuClick = onMenuClick, inputname ="Bookmarks") }
            )
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(query, onQueryChanged = {query=it})
            FilterSortRow(selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it },
                selectedSort = selectedSort,
                onSortSelected = { selectedSort = it })

            Spacer(Modifier.height(30.dp))

            BookmarkCard(imageUrl = R.drawable.research_paper, title = "Sample Title")

            Spacer(Modifier.height(10.dp))
            BookmarkCard(imageUrl= R.drawable.research_paper, title = "Sample Title")

            Spacer(Modifier.height(10.dp))
            BookmarkCard(imageUrl = R.drawable.research_paper, title = "Sample Title")




        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkPreview() {

    Bookmark(onMenuClick = {})
}
