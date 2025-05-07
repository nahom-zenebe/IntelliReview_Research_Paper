
package com.example.intellireview_research_paper.ui.screens.category

import BottomNavBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.theme.White
import com.example.intellireview_research_paper.ui.theme.Purple40

import com.example.intellireview_research_paper.ui.theme.PurpleGrey40
import com.example.intellireview_research_paper.ui.theme.lightLavender
import com.example.intellireview_research_paper.ui.components.NavigationItem


@Composable
    fun CreateCategoryScreen(
        onCreateCategory: (String, String) -> Unit // Callback when create button is clicked
    ) {
        var categoryName by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .heightIn(120.dp)

                ){
                Image(
                    painter = painterResource(id = R.drawable.welcome_page_bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Create Category",
                    textAlign = TextAlign.Center,
                    color = White,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 24.dp)
                    .align(Alignment.Center)
                    .offset(y=110.dp)
                    .padding(24.dp)


                )
            }


            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "Catagory Name" ,
                modifier = Modifier.fillMaxWidth(), // Force full width
                textAlign = TextAlign.Start,


                )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                shape = RoundedCornerShape(26.dp),



                modifier = Modifier
                    .width(360.dp)
                    .padding(bottom = 12.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Description"
            )
            TextField(
                value = description,
                onValueChange = { description = it },



                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onCreateCategory(categoryName, description) },
                modifier = Modifier.fillMaxWidth(),
                enabled = categoryName.isNotBlank(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,  // Background color (Purple)
                    contentColor = White           // Text/icon color
                )

            ) {
                Text("Create")
            }

            BottomNavBar(2, onItemSelected={},Modifier)
        }
    }

@Preview(showBackground = true)
    @Composable
    fun CreateCategoryScreenPreview() {
       MaterialTheme {
          CreateCategoryScreen(onCreateCategory = { name, desc ->
               println("Creating category: $name with description: $desc") })
          }
      }
