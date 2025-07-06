package com.kidshealth.app.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidshealth.app.ui.theme.KidsHealthPrimary
import com.kidshealth.app.ui.theme.KidsHealthBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KidsHealthBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(412.dp)
                .fillMaxHeight()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            
            // Logo
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(KidsHealthPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "❤️",
                    fontSize = 40.sp
                )
            }
            
            Spacer(modifier = Modifier.height(25.dp))
            
            // App Title
            Text(
                text = "KidsHealth",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Tagline
            Text(
                text = "Your child's health, our priority",
                fontSize = 20.sp,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Quote Card
            Card(
                modifier = Modifier
                    .width(373.dp)
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "💗",
                        fontSize = 50.sp
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "\"Every child deserves the best start in life.\nTracking their health journey is a gift\nof love\"\n\n-Health for Every Child",
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(38.dp))
            
            // Feature Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .width(159.dp)
                        .height(115.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Schedule\nAppointment",
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Card(
                    modifier = Modifier
                        .width(159.dp)
                        .height(115.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Track Growth",
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(65.dp))
            
            // Login Button
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .width(373.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KidsHealthPrimary)
            ) {
                Text(
                    text = "Login to Your Account",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(51.dp))
            
            // Create Account Button
            OutlinedButton(
                onClick = onCreateAccountClick,
                modifier = Modifier
                    .width(373.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = KidsHealthPrimary
                )
            ) {
                Text(
                    text = "Create New Account",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(37.dp))
            
            // Footer Text
            Text(
                text = "Secure . Private . Always Available",
                fontSize = 20.sp,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}