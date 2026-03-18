package com.streamflix.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.streamflix.app.data.StreamContent
import com.streamflix.app.ui.theme.*

@Composable
fun DetailScreen(contentId: String, navController: NavController) {
    val content = SampleData.findContent(contentId) ?: return
    var expanded by remember { mutableStateOf(false) }
    var inList by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).background(BgPrimary)) {
        // Hero
        Box(modifier = Modifier.fillMaxWidth().height(350.dp)) {
            Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(content.gradientColors))) {
                Icon(content.icon, null, tint = Color.White.copy(0.1f), modifier = Modifier.size(120.dp).align(Alignment.Center).offset(60.dp, (-30).dp))
            }
            Box(modifier = Modifier.fillMaxWidth().height(200.dp).align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, BgPrimary.copy(0.6f), BgPrimary))))

            // Back button
            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(top = 8.dp, start = 8.dp)) {
                Icon(Icons.Default.ArrowBack, null, tint = Color.White)
            }

            Column(modifier = Modifier.align(Alignment.BottomStart).padding(20.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (content.isNew) BadgeChip("NEW", PrimaryRed)
                    if (content.is4K) BadgeChip("4K HDR", AccentGold)
                    BadgeChip(content.maturityRating, BgElevated)
                }
                Spacer(Modifier.height(12.dp))
                Text(content.title, fontSize = 30.sp, fontWeight = FontWeight.Black, color = Color.White)
                Text(content.subtitle, fontSize = 14.sp, color = TextSecondary)
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = AccentGold, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(content.ratingFormatted, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AccentGold)
                    MetaDot(); Text("${content.year}", fontSize = 13.sp, color = TextSecondary)
                    MetaDot(); Text(content.duration, fontSize = 13.sp, color = TextSecondary)
                }
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    content.genres.forEach { g ->
                        Surface(shape = RoundedCornerShape(50), color = BgSurface2) {
                            Text(g, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextSecondary, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                    }
                }
            }
        }

        // Actions
        Column(modifier = Modifier.padding(20.dp)) {
            // Play button
            Button(
                onClick = { navController.navigate("player/${content.id}") },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Icon(Icons.Default.PlayArrow, null)
                Spacer(Modifier.width(8.dp))
                Text("Play", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            // Action buttons row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                DetailActionBtn(if (inList) Icons.Default.Favorite else Icons.Default.FavoriteBorder, if (inList) "In My List" else "My List", inList) { inList = !inList }
                DetailActionBtn(Icons.Default.Share, "Share")
                DetailActionBtn(Icons.Default.Download, "Download")
                DetailActionBtn(Icons.Default.ThumbUp, "Rate")
            }

            Spacer(Modifier.height(24.dp))

            // Description
            Text(content.description, fontSize = 14.sp, color = TextSecondary, lineHeight = 22.sp, maxLines = if (expanded) Int.MAX_VALUE else 3, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(4.dp))
            Text(if (expanded) "Show Less" else "Read More", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = PrimaryRed,
                modifier = Modifier.clickable { expanded = !expanded })

            Spacer(Modifier.height(24.dp))

            // Cast
            SectionHeader("Cast & Crew", Icons.Default.People)
            Spacer(Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(content.cast) { member ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(70.dp)) {
                        Surface(shape = CircleShape, color = BgElevated, border = BorderStroke(1.dp, Color.White.copy(0.1f)), modifier = Modifier.size(60.dp)) {
                            Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = TextSecondary, modifier = Modifier.size(28.dp)) }
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(member.name.split(" ").first(), fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextPrimary, maxLines = 1)
                        Text(member.role, fontSize = 9.sp, color = TextTertiary, maxLines = 1)
                    }
                }
            }

            // Episodes
            content.episodes?.let { episodes ->
                Spacer(Modifier.height(24.dp))
                SectionHeader("Episodes", Icons.Default.VideoLibrary)
                Spacer(Modifier.height(12.dp))
                episodes.forEach { ep ->
                    EpisodeRow(ep, content) { navController.navigate("player/${content.id}") }
                    Spacer(Modifier.height(8.dp))
                }
            }

            // Similar
            Spacer(Modifier.height(24.dp))
            SectionHeader("More Like This", Icons.Default.AutoAwesome)
            Spacer(Modifier.height(12.dp))
        }
        ContentRowView(SampleData.allContent.filter { it.id != contentId }.shuffled().take(6), navController)
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun DetailActionBtn(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, active: Boolean = false, onClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Icon(icon, null, tint = if (active) PrimaryRed else TextSecondary, modifier = Modifier.size(24.dp))
        Spacer(Modifier.height(6.dp))
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = TextTertiary)
    }
}

@Composable
fun EpisodeRow(ep: com.streamflix.app.data.Episode, content: StreamContent, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(BgSurface)
            .clickable(onClick = onClick).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thumb
        Box(
            modifier = Modifier.width(110.dp).height(65.dp).clip(RoundedCornerShape(6.dp))
                .background(Brush.linearGradient(content.gradientColors.map { it.copy(0.5f) })),
            contentAlignment = Alignment.Center
        ) {
            Surface(shape = CircleShape, color = Color.White.copy(0.15f), modifier = Modifier.size(28.dp)) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(12.dp)) }
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text("E${ep.number}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryRed)
                Spacer(Modifier.width(8.dp))
                Text(ep.title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Text(ep.description, fontSize = 11.sp, color = TextTertiary, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(ep.duration, fontSize = 10.sp, color = TextTertiary, fontWeight = FontWeight.Medium)
        }
    }
}
