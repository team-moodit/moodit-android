package com.swyp.moodit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainBottomBarTab(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTitle: String,
    val route: BottomBarRoute
) {
    Home(
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Rounded.Home,
        iconTitle = "Home",
        route = BottomBarRoute.Home
    ),
    ROUND(
        selectedIcon = Icons.Default.Archive,
        unselectedIcon = Icons.Rounded.Archive,
        iconTitle = "Round",
        route = BottomBarRoute.Round
    ),
    REPORT(
        selectedIcon = Icons.Default.Menu,
        unselectedIcon = Icons.Rounded.Menu,
        iconTitle = "Report",
        route = BottomBarRoute.Report
    )
}