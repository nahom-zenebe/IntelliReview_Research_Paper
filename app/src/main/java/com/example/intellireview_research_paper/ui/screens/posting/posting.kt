package com.example.intellireview_research_paper.ui.components

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.viewmodel.CreatePostViewModel
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostingScreen(
    viewModel: CreatePostViewModel = viewModel()
) {
    val context = LocalContext.current

    var researchTitle by remember { mutableStateOf("") }
    var authorsText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedFile by remember { mutableStateOf<File?>(null) }
    val categories = listOf("AI", "Maths", "Programming")

    // PDF picker launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = copyUriToFile(context, it)
            selectedFile = file
            viewModel.pdfUrl.value = file.absolutePath
        }
    }

    Column(Modifier.fillMaxSize()) {
        // Header image + title
        Box(
            Modifier
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
            Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White.copy(alpha = 0.95f)
        ) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Title field
                OutlinedTextField(
                    value = researchTitle,
                    onValueChange = {
                        researchTitle = it
                        viewModel.title.value = it
                    },
                    label = { Text("Research Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                // Authors field
                OutlinedTextField(
                    value = authorsText,
                    onValueChange = {
                        authorsText = it
                        viewModel.authors.value = it
                    },
                    label = { Text("Authors") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                // Category dropdown
                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = if (selectedCategory.isEmpty()) "Choose category" else selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.clickable { expanded = true }
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
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    selectedCategory = cat
                                    viewModel.category.value = cat
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // PDF Upload / Change button
                Button(
                    onClick = { launcher.launch("application/pdf") },
                    Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(
                        text = if (selectedFile == null) "Upload PDF" else "Change PDF",
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                selectedFile?.let { Text("Selected: ${it.name}") }

                // POST button
                Button(
                    onClick = {
                        if (researchTitle.isBlank() || authorsText.isBlank() ||
                            selectedCategory.isBlank() || selectedFile == null
                        ) {
                            Toast.makeText(
                                context,
                                "Fill all fields & choose a PDF",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.createPaper()
                            Toast.makeText(
                                context,
                                "Uploading…",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    Modifier.fillMaxWidth()
                ) {
                    Text("POST")
                }
            }
        }
    }
}

/**
 * Copy the content of a URI to a temporary file and return it.
 */
private fun copyUriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Cannot open input stream for URI: $uri")
    val tempFile = File.createTempFile("upload_", ".pdf", context.cacheDir)
    FileOutputStream(tempFile).use { output ->
        inputStream.use { input ->
            input.copyTo(output)
        }
    }
    return tempFile
}
