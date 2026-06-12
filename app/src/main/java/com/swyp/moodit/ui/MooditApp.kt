package com.swyp.moodit.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.swyp.moodit.designsystem.component.MainBottomBar
import com.swyp.moodit.navigation.MainBottomBarTab
import com.swyp.moodit.navigation.MainBottomBarTab.Companion.toItemData
import com.swyp.moodit.navigation.MooditNavHost

@Composable
internal fun MooditApp(
    appState: MooditAppState,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    MooditAppContent(
        appState = appState,
        modifier = modifier,
        snackbarHostState = snackbarHostState
    )
}

@Composable
internal fun MooditAppContent(
    appState: MooditAppState,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            MainBottomBar(
                modifier = Modifier.navigationBarsPadding(),
                visible = appState.showBottomBar(),
                mainNavTabs = MainBottomBarTab.entries.map { it.toItemData() },
                currentTab = appState.currentTab?.toItemData(),
                onTabSelected = { selectedTab ->
                    val targetTab = MainBottomBarTab.entries.find { it.toItemData() == selectedTab }
                    targetTab?.let {
                        appState.navigateToMainBottomBarTab(it)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        MooditNavHost(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                ),
            appState = appState,
            onShowSnackbar = { message, action ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = action,
                    duration = SnackbarDuration.Short
                ) == ActionPerformed
            }
        )
    }
}