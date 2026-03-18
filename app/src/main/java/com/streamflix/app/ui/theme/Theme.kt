package com.streamflix.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val StreamflixColorScheme = darkColorScheme(
    primary = PrimaryRed,
    onPrimary = Color.White,
    primaryContainer = DeepRed,
    secondary = AccentGold,
    onSecondary = Color.Black,
    background = BgPrimary,
    onBackground = TextPrimary,
    surface = BgSurface,
    onSurface = TextPrimary,
    surfaceVariant = BgSurface2,
    onSurfaceVariant = TextSecondary,
    outline = BgElevated,
    error = PrimaryRed
)

@Composable
fun StreamflixTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BgPrimary.toArgb()
            window.navigationBarColor = BgSurface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = StreamflixColorScheme,
        content = content
    )
}
