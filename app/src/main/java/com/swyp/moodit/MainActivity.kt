package com.swyp.moodit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.swyp.moodit.ui.MooditApp
import com.swyp.moodit.ui.rememberMooditAppState
import com.swyp.moodit.ui.theme.MooditTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var isLoading = true
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(3000L)
            isLoading = false
        }

        splashScreen.setKeepOnScreenCondition {
            when (isLoading) {
                true -> true
                false -> false
            }
        }

        enableEdgeToEdge()
        setContent {
            val appState = rememberMooditAppState()
            MooditTheme {
                MooditApp(appState = appState)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MooditTheme {
        Greeting("Android")
    }
}