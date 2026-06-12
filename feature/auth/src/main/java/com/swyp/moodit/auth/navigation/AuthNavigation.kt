package com.swyp.moodit.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.swyp.moodit.auth.route.LoginRoute
import com.swyp.moodit.navigation.AuthRoute

fun NavGraphBuilder.authNavGraph(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<AuthRoute.Login> {
        LoginRoute(onShowSnackbar = onShowSnackbar)
    }
}