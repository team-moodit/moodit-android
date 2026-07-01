package com.swyp.moodit.navigation

import androidx.compose.runtime.Composable
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MainBottomBarItemData

enum class MainBottomBarTab(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTitle: String,
    val route: BottomBarRoute
) {
    HOME(
        selectedIcon = R.drawable.home,
        unselectedIcon = R.drawable.home,
        iconTitle = "홈",
        route = BottomBarRoute.Home
    ),
    TOURNAMENT(
        selectedIcon = R.drawable.moodmatch,
        unselectedIcon = R.drawable.moodmatch,
        iconTitle = "무드매치",
        route = BottomBarRoute.Tournament
    ),
    REPORT(
        selectedIcon = R.drawable.report,
        unselectedIcon = R.drawable.report,
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