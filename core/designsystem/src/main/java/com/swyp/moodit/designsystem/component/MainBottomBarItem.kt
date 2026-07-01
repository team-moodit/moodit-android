package com.swyp.moodit.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MainBottomBarItem(
    modifier: Modifier = Modifier,
    tab: MainBottomBarItemData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) MooditTheme.colors.background else MooditTheme.colors.surface,
        label = "IconColorAnimation"
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else 1.0f, label = "IconScaleAnimation"
    )

    Column(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(
                color = if (isSelected) MooditTheme.colors.primary else Color.Transparent
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) { onClick() }
            .padding(vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val iconId = if (isSelected) tab.selectedIcon else tab.unselectedIcon
        Icon(
            painter = painterResource(iconId),
            contentDescription = tab.iconTitle,
            tint = iconColor,
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer(scaleX = scale, scaleY = scale)
        )

        Text(
            text = tab.iconTitle,
            color = iconColor,
            style = MooditTheme.typography.caption,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview
@Composable
fun MainBottomBarItemPreview() {
    val navTab = MainBottomBarItemData(
        selectedIcon = R.drawable.home, unselectedIcon = R.drawable.home, iconTitle = "Home"
    )

    MooditTheme {
        MainBottomBarItem(
            tab = navTab, isSelected = true, onClick = {})
    }
}
