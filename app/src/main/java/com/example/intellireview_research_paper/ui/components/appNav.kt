
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intellireview_research_paper.ui.navigation.Screen

@Composable
fun BottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavHostController,
    role: String, // Add role as a parameter
    modifier: Modifier = Modifier
) {
    // Set different items based on the user's role
    val items = if (role == "admin") {
        listOf(
            Screen.AdminDashboard,
            Screen.Favourites,
            Screen.createCategory,
            Screen.CreateNotification,
            Screen.Profile
        )
    } else {
        listOf(
            Screen.Home,
            Screen.Favourites,
            Screen.Grid,
            Screen.Messages,
            Screen.Profile
        )
    }

    // Define icons for user and admin
    val userIcons = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.FavoriteBorder,
        Icons.Outlined.GridView,
        Icons.Outlined.Send,
        Icons.Outlined.Person
    )

    val adminIcons = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.FavoriteBorder,
        Icons.Outlined.Add,
        Icons.Outlined.NotificationsActive,
        Icons.Outlined.Person
    )

    // Select the appropriate icons based on the role
    val icons = if (role == "admin") adminIcons else userIcons

    NavigationBar(
        containerColor = Color(0xFFECECFB),
        tonalElevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        items.forEachIndexed { index, screen ->
            val isSelected = selectedItem == index
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onItemSelected(index)
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 6.dp)
                    ) {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = screen.route,
                            modifier = Modifier.size(26.dp),
                            tint = Color(0xFF444444)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(2.dp)
                                    .background(Color(0xFF5D5CBB))
                            )
                        } else {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}
