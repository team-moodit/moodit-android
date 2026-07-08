package com.swyp.moodit.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.swyp.moodit.designsystem.theme.MooditTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MooditTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    navigationIcon: @Composable () -> Unit = {},
    actionIcon: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MooditTheme.colors.background,
        titleContentColor = MooditTheme.colors.onBackground,
        navigationIconContentColor = MooditTheme.colors.onTertiary,
        actionIconContentColor = MooditTheme.colors.onTertiary
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    if (textAlign == TextAlign.Center) {
        CenterAlignedTopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            actions = actionIcon,
            colors = colors,
            modifier = modifier,
            scrollBehavior = scrollBehavior
        )
    } else {
        TopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            actions = actionIcon,
            colors = colors,
            modifier = modifier,
            scrollBehavior = scrollBehavior
        )
    }
}