package com.swyp.moodit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.swyp.moodit.ui.MooditApp
import com.swyp.moodit.ui.rememberMooditAppState
import com.swyp.moodit.ui.theme.MooditTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.state.value.isLoading
        }

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(
            Color.Transparent.toArgb()
        ),
            navigationBarStyle = SystemBarStyle.dark(
                Color.Transparent.toArgb()
            )
        )

        setContent {
            val appState = rememberMooditAppState()

            LaunchedEffect(Unit) {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        is MainSideEffect.NavigateToLogin -> {
                            appState.navigateToLogin()
                        }

                        is MainSideEffect.NavigateToHome -> {
                            appState.navigateToMain()
                        }

                        is MainSideEffect.NavigateToOnBoarding -> {
                            appState.navigateToOnBoarding()
                        }
                    }
                }
            }
            MooditTheme {
                MooditApp(appState = appState)
            }
        }
    }
}