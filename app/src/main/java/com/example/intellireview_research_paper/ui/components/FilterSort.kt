@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FilterSortRow(
    selectedFilter: String?,
    onFilterSelected: (String) -> Unit,
    selectedSort: String?,
    onSortSelected: (String) -> Unit
) {
    val filterOptions = listOf("All", "Favorites", "Recent")
    val sortOptions   = listOf("Name", "Date", "Rating")

    var filterExpanded by remember { mutableStateOf(false) }
    var sortExpanded   by remember { mutableStateOf(false) }

    var filterSelection by remember { mutableStateOf(selectedFilter ?: "Filter") }
    var sortSelection   by remember { mutableStateOf(selectedSort   ?: "Sort") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ─── Filter field ───────────────────────
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value         = filterSelection,
                onValueChange = { /* no-op */ },
                readOnly      = true,
                placeholder   = { Text("Filter") },
                trailingIcon  = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Show filter options",
                        modifier = Modifier.clickable { filterExpanded = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape  = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFECECFB),
                    focusedContainerColor   = Color(0xFFECFBFF),
                    unfocusedBorderColor    = Color.Transparent,
                    focusedBorderColor      = Color.Transparent
                )
            )

            DropdownMenu(
                expanded        = filterExpanded,
                onDismissRequest = { filterExpanded = false },
                modifier        = Modifier.fillMaxWidth()
            ) {
                filterOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            filterSelection = option
                            onFilterSelected(option)
                            filterExpanded = false
                        }
                    )
                }
            }
        }

        // ─── Sort field ─────────────────────────
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value         = sortSelection,
                onValueChange = { /* no-op */ },
                readOnly      = true,
                placeholder   = { Text("Sort") },
                trailingIcon  = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Show sort options",
                        modifier = Modifier.clickable { sortExpanded = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape  = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFECECFB),
                    focusedContainerColor   = Color(0xFFECFBFF),
                    unfocusedBorderColor    = Color.Transparent,
                    focusedBorderColor      = Color.Transparent
                )
            )

            DropdownMenu(
                expanded        = sortExpanded,
                onDismissRequest = { sortExpanded = false },
                modifier        = Modifier.fillMaxWidth()
            ) {
                sortOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            sortSelection = option
                            onSortSelected(option)
                            sortExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterSortRowPreview() {
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf<String?>(null) }

    FilterSortRow(
        selectedFilter = selectedFilter,
        onFilterSelected = { selectedFilter = it },
        selectedSort = selectedSort,
        onSortSelected = { selectedSort = it }
    )
}