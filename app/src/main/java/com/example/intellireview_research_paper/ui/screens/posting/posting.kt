
package com.example.intellireview_research_paper.ui.components

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.viewmodel.CreatePostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostingScreen(viewModel: CreatePostViewModel) {
    val context = LocalContext.current
    var researchTitle by remember { mutableStateOf(TextFieldValue()) }
    var authors by remember { mutableStateOf(TextFieldValue()) }
    var selectedCategory by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }
    var filePath by remember { mutableStateOf<String?>(null) }
    val categories = listOf("AI", "Maths", "Programming")

    val fileLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val path = getRealPathFromURI(context, it)
            filePath = path
            viewModel.pdfUrl.value = path
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.welcome_page_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Text(
                text = "Create a Post",
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White.copy(alpha = 0.95f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = researchTitle,
                    onValueChange = {
                        researchTitle = it
                        viewModel.title.value = it.text
                    },
                    label = { Text("Research Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = authors,
                    onValueChange = {
                        authors = it
                        viewModel.authors.value = it.text
                    },
                    label = { Text("Authors") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)) {
                    OutlinedTextField(
                        value = if (selectedCategory.isEmpty()) "Choose category" else selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Show category options",
                                modifier = Modifier.clickable { categoryExpanded = true }
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    viewModel.category.value = category
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = { fileLauncher.launch("application/pdf") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Upload PDF", color = Color.Black)
                        Icon(
                            imageVector = Icons.Filled.Image,
                            contentDescription = "Upload",
                            tint = Color.Black
                        )
                    }
                }

                Button(
                    onClick = {
                        if (researchTitle.text.isNotEmpty() &&
                            authors.text.isNotEmpty() &&
                            selectedCategory.isNotEmpty() &&
                            filePath != null) {
                            viewModel.createPaper()
                        } else {
                            Toast.makeText(
                                context,
                                "Please fill all fields and select a PDF file",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("POST")
                }
            }
        }
    }
}

private fun getRealPathFromURI(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        if (it.moveToFirst()) {
            val idx = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
            if (idx >= 0) it.getString(idx) else uri.path
        } else {
            uri.path
        }
    }
}