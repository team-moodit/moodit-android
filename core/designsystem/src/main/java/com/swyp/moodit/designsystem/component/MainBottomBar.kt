package com.swyp.moodit.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeChild

/*@Composable
fun MainBottomBar(
    visible: Boolean,
    currentTab: MainNavTab,
    onTabSelected: (MainNavTab) -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(100.dp))
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        backgroundColor = Color(0xFF111111),
                        tint = Color(0xFF111111).copy(alpha = 0.76f),
                        blurRadius = 16.dp
                    )
                )
                .border(
                    width = 1.dp,
                    color = MooditTheme.colors.onSurfaceContainer,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                mainNavTabs.forEach { item ->
                    val isSelected = currentTab == item

                    MainBottomBarItem(
                        isSelected = isSelected,
                        tab = item,
                        onClick = { onTabSelected(item) }
                    )
                }
            }
        }
    }
} */

@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    visible: Boolean,
    mainNavTabs: List<MainBottomBarItemData>,
    currentTab: MainBottomBarItemData?,
    onTabSelected: (MainBottomBarItemData) -> Unit
) {
    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = modifier.wrapContentSize()
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .hazeChild(
                        state = hazeState,
                        shape = RoundedCornerShape(100.dp),
                        style = HazeStyle(
                            tint = HazeTint(Color(0xFF111111).copy(alpha = 0.76f)),
                            blurRadius = 16.dp
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .border(
                        1.dp,
                        MooditTheme.colors.onSurfaceContainer,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                mainNavTabs.forEach { item ->
                    val isSelected = currentTab == item
                    MainBottomBarItem(
                        isSelected = isSelected,
                        tab = item,
                        onClick = { onTabSelected(item) }
                    )
                }
            }
        }
    }
}

data class MainBottomBarItemData(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTitle: String
)

@Preview
@Composable
fun MainBottomBarPreview() {
    val navTabs = listOf(
        MainBottomBarItemData(
            selectedIcon = R.drawable.home,
            unselectedIcon = R.drawable.home,
            iconTitle = "홈"
        ),
        MainBottomBarItemData(
            selectedIcon = R.drawable.moodmatch,
            unselectedIcon = R.drawable.moodmatch,
            iconTitle = "무드매치"
        ),
        MainBottomBarItemData(
            selectedIcon = R.drawable.report,
            unselectedIcon = R.drawable.report,
            iconTitle = "리포트"
        )
    )
    MooditTheme {
        MainBottomBar(
            visible = true,
            hazeState = HazeState(),
            mainNavTabs = navTabs,
            currentTab = navTabs[0],
            onTabSelected = {}
        )
    }
}