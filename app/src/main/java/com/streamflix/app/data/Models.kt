package com.streamflix.app.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class StreamContent(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: ImageVector,
    val gradientColors: List<Color>,
    val rating: Double,
    val year: Int,
    val duration: String,
    val genres: List<String>,
    val maturityRating: String,
    val isTrending: Boolean = false,
    val isNew: Boolean = false,
    val is4K: Boolean = false,
    val contentType: String,
    val cast: List<CastMember> = emptyList(),
    val episodes: List<Episode>? = null
) {
    val ratingFormatted: String get() = String.format("%.1f", rating)
    val genreText: String get() = genres.joinToString(" • ")
    val ratingStars: Int get() = (rating / 2).toInt()
}

data class CastMember(
    val name: String,
    val role: String
)

data class Episode(
    val number: Int,
    val title: String,
    val duration: String,
    val description: String,
    val seasonNumber: Int = 1
)

data class WatchProgress(
    val contentId: String,
    val progress: Float,
    val episode: Int? = null
)

data class ContentRow(
    val title: String,
    val icon: ImageVector,
    val items: List<StreamContent>
)
