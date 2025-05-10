package com.example.intellireview_research_paper.ui.components

import FilterSortRow
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.tooling.preview.Preview



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp

@Composable
fun FilterSortCategory(
    selectedFilter: String?,
    onFilterSelected: (String) -> Unit,
    selectedSort: String?,
    onSortSelected: (String) -> Unit,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit
) {
    val filterOptions = listOf("All", "Favorites", "Recent")
    val sortOptions = listOf("Name", "Date", "Rating")
    val categoryOptions = listOf("General", "Work", "Personal", "Urgent")

    var filterExpanded by remember { mutableStateOf(false) }
    var sortExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

    var filterSelection by remember { mutableStateOf(selectedFilter ?: "Filter") }
    var sortSelection by remember { mutableStateOf(selectedSort ?: "Sort") }
    var categorySelection by remember { mutableStateOf(selectedCategory ?: "Category") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // ─── Filter field ───────────────────────
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = filterSelection,
                onValueChange = { /* no-op */ },
                readOnly = true,
                placeholder = { Text("Filter") },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Show filter options",
                        modifier = Modifier.clickable { filterExpanded = true }
                            .width(38.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    ,
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFECECFB),
                    focusedContainerColor = Color(0xFFECFBFF),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                )
            )

            DropdownMenu(
                expanded = filterExpanded,
                onDismissRequest = { filterExpanded = false },
                modifier = Modifier.width(150.dp)
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
                value = sortSelection,
                onValueChange = { /* no-op */ },
                readOnly = true,
                placeholder = { Text("Sort") },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Show sort options",
                        modifier = Modifier.clickable { sortExpanded = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFECECFB),
                    focusedContainerColor = Color(0xFFECFBFF),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                )
            )

            DropdownMenu(
                expanded = sortExpanded,
                onDismissRequest = { sortExpanded = false },
                modifier = Modifier.width(150.dp)
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

        // ─── Category field ─────────────────────
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = categorySelection,
                onValueChange = { /* no-op */ },
                readOnly = true,
                placeholder = { Text("Category") },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Show category options",
                        modifier = Modifier.clickable { categoryExpanded = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .width(102.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFECECFB),
                    focusedContainerColor = Color(0xFFECFBFF),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                )
            )

            DropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false },
                modifier = Modifier.width(150.dp)
            ) {
                categoryOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            categorySelection = option
                            onCategorySelected(option)
                            categoryExpanded = false
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FilterSortCategoryPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        FilterSortCategory (
            selectedFilter = null,
            onFilterSelected = {},
            selectedSort = null,
            onSortSelected = {},
            selectedCategory = null,
            onCategorySelected = {}
        )
    }
}
