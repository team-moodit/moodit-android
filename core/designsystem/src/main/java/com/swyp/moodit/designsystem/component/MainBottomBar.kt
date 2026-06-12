package com.swyp.moodit.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                .height(64.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                ),
            horizontalArrangement = Arrangement.SpaceAround,
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
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTitle: String,
    val onClick: () -> Unit
)

@Preview
@Composable
fun MainBottomBarPreview() {
    val navTabs = listOf(
        MainBottomBarItemData(
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Rounded.Home,
            iconTitle = "Home",
            onClick = {}
        ),
        MainBottomBarItemData(
            selectedIcon = Icons.Default.Archive,
            unselectedIcon = Icons.Rounded.Archive,
            iconTitle = "Round",
            onClick = {}
        ),
        MainBottomBarItemData(
            selectedIcon = Icons.Default.Menu,
            unselectedIcon = Icons.Rounded.Menu,
            iconTitle = "Report",
            onClick = {}
        )
    )
    MaterialTheme {
        MainBottomBar(
            visible = true,
            mainNavTabs = navTabs,
            currentTab = navTabs[0],
            onTabSelected = {}
        )
    }
}