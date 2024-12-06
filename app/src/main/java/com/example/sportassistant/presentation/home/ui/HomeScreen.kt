package com.example.sportassistant.presentation.home.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sportassistant.R
import com.example.sportassistant.presentation.HomeNavGraph
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.Route
import kotlin.math.log

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    logout: () -> Unit,
) {
    BackHandler {  }
    Scaffold (
        bottomBar = {
            getBottomBar(
                navController = navController,
                onTabPressed = { it: Route ->
                    navController.navigate(it.route)
                }
            )
        }
    ) {
        HomeNavGraph(
            navController = navController,
            logout = logout,
        )
    }
}

@Composable
private fun getBottomBar(
    onTabPressed: ((Route) -> Unit),
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val screensWithBottomBar = listOf(
        HomeRoutes.Home.route,
        HomeRoutes.Pinned.route,
        HomeRoutes.Calendar.route,
        HomeRoutes.Settings.route,
        HomeRoutes.Profile.route,
    )
    val navigationItemContentList = listOf(
        NavigationItemContent(
            icon = R.drawable.profile,
            text = stringResource(id = R.string.nav_bar_profile_text),
            route = HomeRoutes.Profile,
        ),
        NavigationItemContent(
            icon = R.drawable.settings,
            text = stringResource(id = R.string.nav_bar_settings_text),
            route = HomeRoutes.Settings,
        ),
        NavigationItemContent(
            icon = R.drawable.calendar,
            text = stringResource(id = R.string.nav_bar_calendar_text),
            route = HomeRoutes.Calendar,
        ),
        NavigationItemContent(
            icon = R.drawable.pin,
            text = stringResource(id = R.string.nav_bar_pinned_text),
            route = HomeRoutes.Pinned,
        )
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (screensWithBottomBar.contains(currentDestination?.route)) {
        NavigationBar(
            modifier = modifier,
            containerColor = Color.Transparent
        ) {
            for (navItem in navigationItemContentList) {
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == navItem.route.route } == true,
                    onClick = { onTabPressed(navItem.route) },
                    icon = {
                        Icon(
                            painter = painterResource(navItem.icon),
                            contentDescription = navItem.text
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                    ),
                    label = { Text(text = navItem.text) },
                )
            }
        }
    }
}

private data class NavigationItemContent(
    val icon: Int,
    val text: String,
    val route: Route,
)
