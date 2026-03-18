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
import com.streamflix.app.data.*
import com.streamflix.app.ui.theme.*

@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val featured = SampleData.featured
    val rows = SampleData.getContentRows()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState).background(BgPrimary)
    ) {
        // Hero Banner
        HeroBanner(featured, navController)

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Watching
        SectionHeader("Continue Watching", Icons.Default.PlayCircle)
        Spacer(modifier = Modifier.height(12.dp))
        ContinueWatchingRow(navController)

        Spacer(modifier = Modifier.height(32.dp))

        // Content Rows
        rows.forEach { row ->
            SectionHeader(row.title, row.icon)
            Spacer(modifier = Modifier.height(12.dp))
            ContentRowView(row.items, navController)
            Spacer(modifier = Modifier.height(32.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HeroBanner(content: StreamContent, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth().height(480.dp)
    ) {
        // Gradient background
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.linearGradient(content.gradientColors)
            )
        ) {
            // Icon
            Icon(
                content.icon, contentDescription = null,
                tint = Color.White.copy(alpha = 0.08f),
                modifier = Modifier.size(180.dp).align(Alignment.Center).offset(x = 40.dp, y = (-20).dp)
            )
        }

        // Bottom overlay
        Box(
            modifier = Modifier.fillMaxWidth().height(250.dp).align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, BgPrimary.copy(0.5f), BgPrimary.copy(0.85f), BgPrimary)))
        )

        // Content
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // Trending badge
            if (content.isTrending) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = AccentGold.copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, AccentGold.copy(alpha = 0.3f))
                ) {
                    Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TrendingUp, null, tint = AccentGold, modifier = Modifier.size(12.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("TRENDING #1", fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = AccentGold, letterSpacing = 1.5.sp)
                    }
                }
                Spacer(Modifier.height(12.dp))
            }

            Text(content.title, fontSize = 36.sp, fontWeight = FontWeight.Black, color = Color.White, lineHeight = 40.sp)
            Spacer(Modifier.height(4.dp))
            Text(content.subtitle, fontSize = 15.sp, color = TextSecondary)
            Spacer(Modifier.height(12.dp))

            // Meta info
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, null, tint = AccentGold, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text(content.ratingFormatted, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AccentGold)
                MetaDot(); Text("${content.year}", fontSize = 13.sp, color = TextSecondary)
                MetaDot(); Text(content.duration, fontSize = 13.sp, color = TextSecondary)
                MetaDot()
                Surface(shape = RoundedCornerShape(4.dp), border = BorderStroke(1.dp, Color.White.copy(0.4f)), color = Color.Transparent) {
                    Text(content.maturityRating, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(content.genreText, fontSize = 12.sp, color = TextTertiary)
            Spacer(Modifier.height(16.dp))

            // Buttons
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { navController.navigate("player/${content.id}") },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Play", fontWeight = FontWeight.SemiBold)
                }
                OutlinedButton(
                    onClick = { navController.navigate("detail/${content.id}") },
                    border = BorderStroke(1.dp, Color.White.copy(0.15f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(Icons.Default.Info, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("More Info", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun MetaDot() {
    Spacer(Modifier.width(12.dp))
    Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(TextTertiary))
    Spacer(Modifier.width(12.dp))
}

@Composable
fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = PrimaryRed, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("See All", fontSize = 13.sp, color = TextTertiary)
            Spacer(Modifier.width(4.dp))
            Icon(Icons.Default.ChevronRight, null, tint = TextTertiary, modifier = Modifier.size(14.dp))
        }
    }
}

@Composable
fun ContentRowView(items: List<StreamContent>, navController: NavController) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { content ->
            ContentCard(content) { navController.navigate("detail/${content.id}") }
        }
    }
}

@Composable
fun ContentCard(content: StreamContent, onClick: () -> Unit) {
    Column(
        modifier = Modifier.width(140.dp).clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.width(140.dp).height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.linearGradient(content.gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Icon(content.icon, null, tint = Color.White.copy(0.5f), modifier = Modifier.size(42.dp))

            // Bottom gradient
            Box(modifier = Modifier.fillMaxWidth().height(60.dp).align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.7f)))))

            // Badges
            Column(
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (content.isNew) BadgeChip("NEW", PrimaryRed)
                if (content.is4K) BadgeChip("4K", AccentGold)
            }
        }

        Spacer(Modifier.height(8.dp))
        Text(content.title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)

        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) { i ->
                Icon(Icons.Default.Star, null, tint = AccentGold.copy(if (i < content.ratingStars) 1f else 0.3f), modifier = Modifier.size(9.dp))
            }
            Spacer(Modifier.width(4.dp))
            Text(content.ratingFormatted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = AccentGold)
        }
    }
}

@Composable
fun BadgeChip(text: String, color: Color) {
    Surface(shape = RoundedCornerShape(50), color = color) {
        Text(text, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = Color.White,
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp))
    }
}

@Composable
fun ContinueWatchingRow(navController: NavController) {
    val cwItems = SampleData.continueWatching.mapNotNull { wp ->
        SampleData.findContent(wp.contentId)?.let { it to wp }
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cwItems) { (content, progress) ->
            ContinueWatchingCard(content, progress) { navController.navigate("detail/${content.id}") }
        }
    }
}

@Composable
fun ContinueWatchingCard(content: StreamContent, progress: WatchProgress, onClick: () -> Unit) {
    Column(modifier = Modifier.width(170.dp).clickable(onClick = onClick)) {
        Box(
            modifier = Modifier.width(170.dp).height(96.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.linearGradient(content.gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Icon(content.icon, null, tint = Color.White.copy(0.5f), modifier = Modifier.size(32.dp))

            // Play button
            Surface(shape = CircleShape, color = Color.White.copy(0.15f), modifier = Modifier.size(36.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }

            // Episode badge
            progress.episode?.let { ep ->
                Surface(
                    shape = RoundedCornerShape(50), color = Color.Black.copy(0.7f),
                    modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
                ) {
                    Text("S1:E$ep", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
        }

        // Progress bar
        Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(BgElevated)) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(progress.progress).background(PrimaryRed))
        }

        Spacer(Modifier.height(8.dp))
        Text(content.title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text("${(progress.progress * 100).toInt()}% watched", fontSize = 10.sp, color = TextTertiary)
    }
}
