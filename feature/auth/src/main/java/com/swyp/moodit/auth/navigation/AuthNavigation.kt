package com.swyp.moodit.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.swyp.moodit.auth.login.LoginRoute
import com.swyp.moodit.navigation.AuthRoute

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToMain: () -> Unit
) {
    composable<AuthRoute.Login> {
        LoginRoute(
            navigateToMain = navigateToMain,
            onShowSnackbar = onShowSnackbar
        )
    }
}

fun NavController.navigateToLogin() {
    navigate(route = AuthRoute.Login) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}