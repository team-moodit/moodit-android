package com.swyp.moodit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.swyp.moodit.auth.navigation.authNavGraph

@Composable
fun MooditNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoute.Login
    ) {
        authNavGraph(onShowSnackbar = onShowSnackbar)
    }
}