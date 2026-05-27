package com.echo.features.main.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.echo.core.network.di.AppComponent
import com.echo.core.uikit.utils.daggerViewModel
import com.echo.features.event.di.DaggerEventDetailsComponent
import com.echo.features.event.presentation.screens.EventDetailsScreen
import com.echo.features.profile.di.DaggerProfileComponent

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    appComponent: AppComponent,
    onLogout: () -> Unit,
    authKey: Long = 0,
    onThemeToggle: () -> Unit = {}
    ) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var selectedEventId by remember { mutableStateOf<Int?>(null) }

    val profileComponent = remember(authKey) {
        DaggerProfileComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    val bottomNavItems = listOf<BottomNavItem>(
        BottomNavItem(
            route = MainRoutes.FEED,
            label = "Лента",
            icon = { Icon(Icons.Rounded.Home, contentDescription = "Лента") }
        ),
        BottomNavItem(
            route = MainRoutes.CATEGORIES,
            label = "Интересы",
            icon = { Icon(Icons.Rounded.FavoriteBorder, contentDescription = "Интересы") }
        ),
        BottomNavItem(
            route = MainRoutes.FRIENDS,
            label = "Друзья",
            icon = { Icon(Icons.Rounded.People, contentDescription = "Друзья") }
        ),
        BottomNavItem(
            route = MainRoutes.PROFILE,
            label = "Профиль",
            icon = { Icon(Icons.Rounded.Person, contentDescription = "Профиль") }
        )
    )

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp
            ) {
                NavigationBar(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp
                ) {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(MainRoutes.FEED) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = item.icon,
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                selectedTextColor = MaterialTheme.colorScheme.outline,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.outline,
                                unselectedTextColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavHost(
                appComponent = appComponent,
                navController = navController,
                onEventClick = { eventId ->
                    selectedEventId = eventId
                },
                profileComponent = profileComponent,
                onLogout = onLogout,
                authKey = authKey,
                onThemeToggle = onThemeToggle
            )

            selectedEventId?.let { eventId ->
                val eventComponent = remember {
                    DaggerEventDetailsComponent.builder()
                        .appComponent(appComponent)
                        .build()
                }
                EventDetailsScreen(
                    eventId = eventId,
                    viewModel = daggerViewModel { eventComponent.getViewModel() },
                    onDismiss = { selectedEventId = null }
                )
            }
        }
    }
}