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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.streamflix.app.ui.theme.*

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(BgPrimary)
    ) {
        Text("Profile", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary,
            modifier = Modifier.padding(20.dp))

        // Avatar
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                shape = CircleShape,
                color = PrimaryRed,
                shadowElevation = 12.dp,
                modifier = Modifier.size(90.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(36.dp))
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("Clinton", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text("Premium Member", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AccentGold)
            Text("Member since 2026", fontSize = 11.sp, color = TextTertiary)
        }

        Spacer(Modifier.height(20.dp))

        // Stats
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = BgSurface,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                ProfileStat(Icons.Default.Visibility, "3", "Watched", Modifier.weight(1f))
                Box(modifier = Modifier.width(1.dp).height(50.dp).background(BgElevated))
                ProfileStat(Icons.Default.Favorite, "3", "My List", Modifier.weight(1f))
                Box(modifier = Modifier.width(1.dp).height(50.dp).background(BgElevated))
                ProfileStat(Icons.Default.Schedule, "6h", "Watch Time", Modifier.weight(1f))
            }
        }

        Spacer(Modifier.height(24.dp))

        // Settings
        SettingsSection("Settings", Icons.Default.Settings) {
            SettingsRow(Icons.Default.AccountCircle, "Edit Profile", iconColor = Info)
            SettingsRow(Icons.Default.Notifications, "Notifications", iconColor = AccentGold, isToggle = true, isOn = true)
            SettingsRow(Icons.Default.ClosedCaption, "Subtitles", iconColor = Success, isToggle = true, isOn = false)
            SettingsRow(Icons.Default.PlayCircle, "Autoplay", iconColor = PrimaryRed, isToggle = true, isOn = true)
            SettingsRow(Icons.Default.Wifi, "Download Over Wi-Fi Only", iconColor = Info, isToggle = true, isOn = true)
            SettingsRow(Icons.Default.Tv, "Video Quality", subtitle = "Auto", iconColor = AccentGold)
        }

        Spacer(Modifier.height(24.dp))

        // About
        SettingsSection("About", Icons.Default.Info) {
            SettingsRow(Icons.Default.Description, "Terms of Service", iconColor = TextSecondary)
            SettingsRow(Icons.Default.BackHand, "Privacy Policy", iconColor = TextSecondary)
            SettingsRow(Icons.Default.Help, "Help Center", iconColor = TextSecondary)
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Streamflix v1.0.0", fontSize = 11.sp, color = TextTertiary)
                Text("Made with ♥ using Kotlin & Jetpack Compose", fontSize = 10.sp, color = TextTertiary)
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun ProfileStat(icon: ImageVector, value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, tint = PrimaryRed, modifier = Modifier.size(16.dp))
        Spacer(Modifier.height(8.dp))
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(label, fontSize = 11.sp, color = TextTertiary)
    }
}

@Composable
fun SettingsSection(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = PrimaryRed, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
        Spacer(Modifier.height(12.dp))
        Surface(shape = RoundedCornerShape(14.dp), color = BgSurface) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingsRow(
    icon: ImageVector, title: String, subtitle: String? = null,
    iconColor: Color = TextPrimary, isToggle: Boolean = false, isOn: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { }.padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = iconColor, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            subtitle?.let { Text(it, fontSize = 11.sp, color = TextTertiary) }
        }
        if (isToggle) {
            Box(modifier = Modifier.size(10.dp).background(if (isOn) Success else BgElevated, CircleShape))
        } else {
            Icon(Icons.Default.ChevronRight, null, tint = TextTertiary, modifier = Modifier.size(14.dp))
        }
    }
}
