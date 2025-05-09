import android.R.attr.description
import android.R.attr.label
import android.R.attr.type
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.*
import androidx.compose.material3.TextField
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.example.intellireview_research_paper.ui.theme.White

import com.example.intellireview_research_paper.ui.theme.deepBlue

@Composable
fun NotificationScreen(

) {
    var type by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Notification",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp).offset(8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier=Modifier.fillMaxWidth()
                .background(deepBlue)
                .padding(12.dp)
                ,



            ) {

                Spacer(modifier = Modifier.height(12.dp))
                // Label TextField (smaller)
                Text(
                    modifier = Modifier.fillMaxWidth().offset(x = 12.dp),
                    text = "Type",
                    fontWeight = FontWeight.Normal,
                    fontSize = 23.sp,
                    color = White


                )
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = type,
                    onValueChange = { type = it },

                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE6E6FA), // Light Lavender
                        unfocusedContainerColor = Color(0xFFE6E6FA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(),

                    )
                Spacer(modifier = Modifier.height(32.dp))
                // Description TextField (larger)
                Text(
                    modifier = Modifier.fillMaxWidth().offset(x = 12.dp),
                    text = "Description",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Normal,
                    color = White


                )
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },

                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE6E6FA),
                        unfocusedContainerColor = Color(0xFFE6E6FA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp), // Wider and taller

                    singleLine = false // Allow multiline input
                )
                Spacer(modifier = Modifier.height(32.dp))
                // Create Button (Purple-Grey)
                Button(
                    onClick = { TODO()},
                    modifier = Modifier
                        .width(190.dp)
                        .height(35.dp),
                    shape = RoundedCornerShape(12.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Create",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    MaterialTheme {
        NotificationScreen()
    }
}