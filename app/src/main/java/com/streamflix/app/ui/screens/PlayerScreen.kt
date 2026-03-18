package com.streamflix.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.streamflix.app.data.SampleData
import com.streamflix.app.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun PlayerScreen(contentId: String, navController: NavController) {
    val content = SampleData.findContent(contentId) ?: return
    var isPlaying by remember { mutableStateOf(false) }
    var currentTime by remember { mutableIntStateOf(0) }
    var showControls by remember { mutableStateOf(true) }
    var isBuffering by remember { mutableStateOf(true) }
    val totalDuration = 120

    // Simulate buffering then auto-play
    LaunchedEffect(Unit) {
        delay(1500)
        isBuffering = false
        isPlaying = true
    }

    // Timer
    LaunchedEffect(isPlaying) {
        while (isPlaying && currentTime < totalDuration) {
            delay(1000)
            currentTime++
        }
        if (currentTime >= totalDuration) isPlaying = false
    }

    // Auto-hide controls
    LaunchedEffect(showControls, isPlaying) {
        if (showControls && isPlaying) {
            delay(4000)
            showControls = false
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Brush.linearGradient(content.gradientColors))
            .clickable { showControls = !showControls }
    ) {
        // Background icon
        Icon(content.icon, null, tint = Color.White.copy(0.1f), modifier = Modifier.size(100.dp).align(Alignment.Center))

        // Buffering spinner
        if (isBuffering) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.Center))
        }

        // Controls overlay
        if (showControls) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp, start = 16.dp, end = 16.dp).align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Surface(shape = CircleShape, color = Color.White.copy(0.1f), modifier = Modifier.size(40.dp)) {
                        Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.KeyboardArrowDown, null, tint = Color.White) }
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(content.title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    if (content.episodes != null) {
                        Text("S1:E1 - ${content.episodes.first().title}", fontSize = 11.sp, color = TextSecondary)
                    }
                }
                IconButton(onClick = {}) {
                    Surface(shape = CircleShape, color = Color.White.copy(0.1f), modifier = Modifier.size(40.dp)) {
                        Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.MoreVert, null, tint = Color.White) }
                    }
                }
            }

            // Center controls
            Row(
                modifier = Modifier.align(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { currentTime = maxOf(currentTime - 10, 0) }) {
                    Icon(Icons.Default.Replay10, null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Surface(
                    shape = CircleShape, color = Color.White.copy(0.15f),
                    modifier = Modifier.size(72.dp),
                    onClick = { isPlaying = !isPlaying }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                }
                IconButton(onClick = { currentTime = minOf(currentTime + 10, totalDuration) }) {
                    Icon(Icons.Default.Forward10, null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }

            // Bottom controls
            Column(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.8f))))
                    .padding(20.dp)
            ) {
                val progress = if (totalDuration > 0) currentTime.toFloat() / totalDuration else 0f

                // Seek bar
                Slider(
                    value = progress,
                    onValueChange = { currentTime = (it * totalDuration).toInt() },
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryRed,
                        activeTrackColor = PrimaryRed,
                        inactiveTrackColor = Color.White.copy(0.2f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Time
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(formatPlayerTime(currentTime), fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.White)
                    Text("-${formatPlayerTime(totalDuration - currentTime)}", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.White)
                }

                Spacer(Modifier.height(12.dp))

                // Action row
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = {}) { Icon(Icons.Default.VolumeUp, null, tint = Color.White) }
                    Row {
                        IconButton(onClick = {}) { Icon(Icons.Default.VideoLibrary, null, tint = Color.White) }
                        IconButton(onClick = {}) { Icon(Icons.Default.SkipNext, null, tint = Color.White) }
                    }
                    IconButton(onClick = {}) { Icon(Icons.Default.Fullscreen, null, tint = Color.White) }
                }
            }
        }
    }
}

private fun formatPlayerTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "$m:${s.toString().padStart(2, '0')}"
}
