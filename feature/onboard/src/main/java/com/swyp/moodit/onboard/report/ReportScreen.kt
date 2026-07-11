package com.swyp.moodit.onboard.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.onboard.component.OnboardingIndicator

@Composable
fun ReportScreen(
    onStartClick: () -> Unit
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MooditFilledButton(
                text = "무딧 시작하기",
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                    .navigationBarsPadding(),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            OnboardingIndicator(selectedIndex = 2)
            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(R.drawable.onboarding_3),
                contentDescription = "image_save_taste",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "쌓을수록 내 취향이 선명해져요",
                style = MooditTheme.typography.h1,
                color = MooditTheme.colors.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "선택한 취향을 직접 경험해 보고 만족도를 남겨\n" +
                        "나만의 취향 리포트를 완성해보세요",
                style = MooditTheme.typography.b2Small,
                color = MooditTheme.colors.textSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    MooditTheme {
        ReportScreen(onStartClick = {})
    }
}