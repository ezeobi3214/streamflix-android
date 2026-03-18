package com.streamflix.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.streamflix.app.ui.screens.*
import com.streamflix.app.ui.theme.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StreamflixTheme {
                StreamflixApp()
            }
        }
    }
}

@Composable
fun StreamflixApp() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2500)
        showSplash = false
    }

    Box(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        AnimatedVisibility(visible = showSplash, exit = fadeOut()) {
            SplashScreen()
        }
        AnimatedVisibility(visible = !showSplash, enter = fadeIn()) {
            MainScreen()
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(BgPrimary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.PlayCircle,
                contentDescription = null,
                tint = PrimaryRed,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "STREAMFLIX",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary,
                letterSpacing = 4.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Your World of Entertainment",
                fontSize = 14.sp,
                color = TextTertiary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val tabs = listOf(
        Triple("home", Icons.Default.Home, "Home"),
        Triple("search", Icons.Default.Search, "Search"),
        Triple("mylist", Icons.Default.Favorite, "My List"),
        Triple("profile", Icons.Default.Person, "Profile")
    )

    Scaffold(
        containerColor = BgPrimary,
        bottomBar = {
            if (currentRoute in tabs.map { it.first }) {
                NavigationBar(
                    containerColor = BgSurface,
                    contentColor = TextTertiary,
                    tonalElevation = 0.dp
                ) {
                    tabs.forEach { (route, icon, label) ->
                        NavigationBarItem(
                            selected = currentRoute == route,
                            onClick = {
                                navController.navigate(route) {
                                    popUpTo("home") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(icon, contentDescription = label, modifier = Modifier.size(22.dp)) },
                            label = { Text(label, fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryRed,
                                selectedTextColor = PrimaryRed,
                                unselectedIconColor = TextTertiary,
                                unselectedTextColor = TextTertiary,
                                indicatorColor = BgSurface
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("search") { SearchScreen(navController) }
            composable("mylist") { MyListScreen(navController) }
            composable("profile") { ProfileScreen() }
            composable("detail/{contentId}") { backStackEntry ->
                val contentId = backStackEntry.arguments?.getString("contentId") ?: ""
                DetailScreen(contentId = contentId, navController = navController)
            }
            composable("player/{contentId}") { backStackEntry ->
                val contentId = backStackEntry.arguments?.getString("contentId") ?: ""
                PlayerScreen(contentId = contentId, navController = navController)
            }
        }
    }
}
