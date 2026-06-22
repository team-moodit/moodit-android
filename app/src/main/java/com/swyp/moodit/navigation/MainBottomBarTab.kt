package com.swyp.moodit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.swyp.moodit.designsystem.component.MainBottomBarItemData

enum class MainBottomBarTab(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTitle: String,
    val route: BottomBarRoute
) {
    HOME(
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Rounded.Home,
        iconTitle = "홈",
        route = BottomBarRoute.Home
    ),
    TOURNAMENT(
        selectedIcon = Icons.Default.Archive,
        unselectedIcon = Icons.Rounded.Archive,
        iconTitle = "무드매치",
        route = BottomBarRoute.Tournament
    ),
    REPORT(
        selectedIcon = Icons.Default.Menu,
        unselectedIcon = Icons.Rounded.Menu,
        iconTitle = "리포트",
        route = BottomBarRoute.Report
    );

    companion object {
        @Composable
        fun contains(predicate: @Composable (BottomBarRoute) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }

        fun MainBottomBarTab.toItemData(): MainBottomBarItemData {
            return MainBottomBarItemData(
                selectedIcon = this.selectedIcon,
                unselectedIcon = this.unselectedIcon,
                iconTitle = this.iconTitle
            )
        }
    }
}