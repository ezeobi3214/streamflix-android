package com.streamflix.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var selectedType by remember { mutableStateOf<String?>(null) }

    val genres = listOf("Action","Comedy","Drama","Fantasy","Horror","Mystery","Romance","Sci-Fi","Thriller","Animation","Documentary","Crime")
    val types = listOf("Movie","Series","Documentary","Anime")

    val filtered = SampleData.allContent.filter { c ->
        (searchText.isBlank() || c.title.contains(searchText, true) || c.description.contains(searchText, true) || c.genres.any { it.contains(searchText, true) }) &&
        (selectedGenre == null || c.genres.contains(selectedGenre)) &&
        (selectedType == null || c.contentType == selectedType)
    }

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        // Title
        Text("Search", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary,
            modifier = Modifier.padding(20.dp))

        // Search bar
        OutlinedTextField(
            value = searchText, onValueChange = { searchText = it },
            placeholder = { Text("Search titles, genres, actors...", color = TextTertiary) },
            leadingIcon = { Icon(Icons.Default.Search, null, tint = TextTertiary) },
            trailingIcon = {
                if (searchText.isNotBlank()) {
                    IconButton(onClick = { searchText = "" }) {
                        Icon(Icons.Default.Close, null, tint = TextTertiary)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryRed.copy(0.5f),
                unfocusedBorderColor = Color.White.copy(0.05f),
                focusedContainerColor = BgSurface2,
                unfocusedContainerColor = BgSurface2,
                cursorColor = PrimaryRed,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )

        Spacer(Modifier.height(12.dp))

        // Type filters
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                FilterChipView("All", selectedType == null) { selectedType = null }
            }
            items(types) { type ->
                FilterChipView(type, selectedType == type) { selectedType = if (selectedType == type) null else type }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Genre filters
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(genres) { genre ->
                FilterChipView(genre, selectedGenre == genre) { selectedGenre = if (selectedGenre == genre) null else genre }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Results
        if (filtered.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().padding(48.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Search, null, tint = TextTertiary.copy(0.5f), modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("No Results", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Text("Try adjusting your search or filters", fontSize = 14.sp, color = TextTertiary)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(filtered) { content ->
                    SearchResultCard(content) { navController.navigate("detail/${content.id}") }
                }
            }
        }
    }
}

@Composable
fun FilterChipView(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (selected) PrimaryRed else Color.Transparent,
        border = BorderStroke(1.dp, if (selected) PrimaryRed else Color.White.copy(0.08f)),
        onClick = onClick
    ) {
        Text(
            label, fontSize = 13.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) Color.White else TextSecondary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun SearchResultCard(content: StreamContent, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        Box(
            modifier = Modifier.fillMaxWidth().aspectRatio(0.67f)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.linearGradient(content.gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Icon(content.icon, null, tint = Color.White.copy(0.4f), modifier = Modifier.size(40.dp))
            Box(modifier = Modifier.fillMaxWidth().height(60.dp).align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.6f)))))
            Row(modifier = Modifier.align(Alignment.BottomStart).padding(8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (content.isNew) BadgeChip("NEW", PrimaryRed)
                if (content.is4K) BadgeChip("4K", AccentGold)
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(content.title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, null, tint = AccentGold, modifier = Modifier.size(11.dp))
            Spacer(Modifier.width(4.dp))
            Text(content.ratingFormatted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = AccentGold)
            Text(" • ", color = TextTertiary, fontSize = 11.sp)
            Text(content.contentType, fontSize = 11.sp, color = TextTertiary)
        }
    }
}
