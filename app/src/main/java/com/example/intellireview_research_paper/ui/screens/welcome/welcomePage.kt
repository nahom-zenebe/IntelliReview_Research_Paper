
import com.example.intellireview_research_paper.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {


        // Top image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_page_bg),
                contentDescription = "Welcome Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            //Overlay Texts on the banner
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // ← Center vertically
            ) {
                Text(
                    text = "Welcome to IntelliReview",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Exploring Ideas, Unveiling Insights – Your Trusted Hub for Research Discoveries!",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }


        // Bottom container with background image
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(450.dp)
                .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_screen_container),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = "Let’s get started",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF36454F)
                )

                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D5CBB)),
                    shape = RoundedCornerShape(25.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "Create new account",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                )

                Button(
                    onClick = onSignUpClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D5CBB)),
                    shape = RoundedCornerShape(25.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text("Sign up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}