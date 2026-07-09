package com.swyp.moodit.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.MainBottomBar
import com.swyp.moodit.designsystem.component.MooditSnackbar
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.designsystem.component.MooditSnackbarVisuals
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.navigation.MainBottomBarTab
import com.swyp.moodit.navigation.MainBottomBarTab.Companion.toItemData
import com.swyp.moodit.navigation.MooditNavHost
import dev.chrisbanes.haze.HazeState

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
    val hazeState = remember { HazeState() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MooditTheme.colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            MainBottomBar(
                modifier = Modifier.fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(start = 40.dp, end = 40.dp, bottom = 20.dp),
                hazeState = hazeState,
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
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.navigationBarsPadding()
            ) { data ->
                MooditSnackbar(snackbarData = data)
            }
        }
    ) { paddingValues ->
        MooditNavHost(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            appState = appState,
            onShowSnackbar = { message, type ->
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    MooditSnackbarVisuals(
                        message = message,
                        type = type ?: MooditSnackbarType.SUCCESS
                    )
                ) == ActionPerformed
            }
        )
    }
}