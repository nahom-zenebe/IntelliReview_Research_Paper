
package com.example.intellireview_research_paper.ui.screens.category

//import androidx.compose.ui.graphics.Color

import BottomNavBar
import android.R.attr.fontWeight
import android.R.attr.password
import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.theme.White


@Composable
fun TopCreateCatagoryImage() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .heightIn(120.dp)

    ){
        Image(
            painter = painterResource(id = R.drawable.welcome_page_cropedbg),
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
                .size(32.dp)


        )
    }
}
@Composable
    fun CreateCategoryScreen(
        onCreateCategory: (String, String) -> Unit // Callback when create button is clicked
    ) {
    var categoryName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize().padding(36.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Category name",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xff36454f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Description",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff36454f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },


                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD3D3D3),
                        focusedContainerColor = Color(0xFFF0F0F0),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff36454f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },

                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD3D3D3),
                        focusedContainerColor = Color(0xFFF0F0F0),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                // onClick = { onLoginClick(email, password) },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(red = 93, green = 92, blue = 187)
                ),
                onClick = TODO(),
                enabled = TODO(),
                shape = TODO(),
                elevation = TODO(),
                border = TODO(),
                interactionSource = TODO()
            ) {
                Text(
                    text = "Create",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))


        }
    }
}

    @Preview(showBackground = true)
    @Composable
    fun CreateCategoryScreenPreview() {
        MaterialTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                TopCreateCatagoryImage() // Make sure this matches your actual composable name
                CreateCategoryScreen(onCreateCategory = { name, desc ->
                    println("Creating category: $name with description: $desc")
                })
            }
        }}

