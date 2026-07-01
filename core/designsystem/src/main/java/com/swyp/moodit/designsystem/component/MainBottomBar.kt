package com.swyp.moodit.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    mainNavTabs: List<MainBottomBarItemData>,
    currentTab: MainBottomBarItemData?,
    onTabSelected: (MainBottomBarItemData) -> Unit
) {
    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(68.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(
                    Color(0xFF111111).copy(0.76f)
                )
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            mainNavTabs.forEach { item ->
                val isSelected = currentTab == item
                MainBottomBarItem(
                    modifier = Modifier.weight(1f),
                    isSelected = isSelected,
                    tab = item,
                    onClick = { onTabSelected(item) }
                )
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
            mainNavTabs = navTabs,
            currentTab = navTabs[0],
            onTabSelected = {}
        )
    }
}