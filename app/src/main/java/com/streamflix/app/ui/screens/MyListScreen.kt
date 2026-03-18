package com.streamflix.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.streamflix.app.data.SampleData
import com.streamflix.app.ui.theme.*

@Composable
fun MyListScreen(navController: NavController) {
    // Using first few items as demo
    var myListIds by remember { mutableStateOf(listOf("neon-ronin", "code-zero", "ember-ash")) }
    val items = myListIds.mapNotNull { SampleData.findContent(it) }

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        Text("My List", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary,
            modifier = Modifier.padding(20.dp))

        if (items.isEmpty()) {
            // Empty state
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(shape = CircleShape, color = BgSurface, modifier = Modifier.size(120.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.HeartBroken, null, tint = TextTertiary.copy(0.5f), modifier = Modifier.size(44.dp))
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Your List is Empty", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(Modifier.height(4.dp))
                    Text("Tap ♥ on any title to add it here", fontSize = 14.sp, color = TextTertiary)
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate("search") { popUpTo("home") { saveState = true }; launchSingleTop = true } },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                        shape = RoundedCornerShape(50)
                    ) {
                        Icon(Icons.Default.Search, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Browse Content", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        } else {
            Text("${items.size} titles", fontSize = 13.sp, color = TextTertiary, modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(Modifier.height(8.dp))

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                items.forEach { content ->
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { navController.navigate("detail/${content.id}") }
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Thumbnail
                        Box(
                            modifier = Modifier.width(100.dp).height(60.dp).clip(RoundedCornerShape(6.dp))
                                .background(Brush.linearGradient(content.gradientColors)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(content.icon, null, tint = Color.White.copy(0.5f), modifier = Modifier.size(24.dp))
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(content.title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, null, tint = AccentGold, modifier = Modifier.size(10.dp))
                                Spacer(Modifier.width(4.dp))
                                Text(content.ratingFormatted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = AccentGold)
                                Text(" • ${content.duration}", fontSize = 11.sp, color = TextTertiary)
                            }
                        }
                        IconButton(onClick = { myListIds = myListIds - content.id }) {
                            Icon(Icons.Default.Favorite, null, tint = PrimaryRed)
                        }
                    }
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}
