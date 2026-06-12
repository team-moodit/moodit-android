package com.swyp.moodit.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.swyp.moodit.auth.route.LoginRoute
import com.swyp.moodit.navigation.AuthRoute

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<AuthRoute.Login> {
        LoginRoute(onShowSnackbar = onShowSnackbar)
    }
}

fun NavController.navigateToLogin() {
    navigate(route = AuthRoute.Login) {
        popUpTo(AuthRoute.Login) { inclusive = true }
    }
}