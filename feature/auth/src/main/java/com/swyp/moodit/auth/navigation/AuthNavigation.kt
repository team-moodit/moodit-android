package com.swyp.moodit.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.swyp.moodit.auth.login.LoginRoute
import com.swyp.moodit.auth.userInfo.InputNicknameRoute
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.navigation.AuthRoute

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToMain: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToInputNickname: (Boolean) -> Unit
) {
    composable<AuthRoute.Login> {
        LoginRoute(
            navigateToMain = navigateToMain,
            navigateToInputNickname = navigateToInputNickname,
            onShowSnackbar = onShowSnackbar
        )
    }

    composable<AuthRoute.InputNickname> {
        InputNicknameRoute(
            navigateToSetting = navigateToSetting,
            navigateToMain = navigateToMain,
            onShowSnackbar = onShowSnackbar
        )
    }
}