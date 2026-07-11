package com.swyp.moodit.onboard.saveTaste

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.onboard.component.OnboardingIndicator

@Composable
fun SaveTasteScreen(
    onNextClick: () -> Unit
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MooditFilledButton(
                text = "다음",
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                    .navigationBarsPadding(),
                containerColor = MooditTheme.colors.onPrimaryContainer,
                contentColor = MooditTheme.colors.primaryContainer
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
            OnboardingIndicator(selectedIndex = 0)
            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(R.drawable.onboarding_1),
                contentDescription = "image_save_taste",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "모으긴 했는데\n내 취향이 뭔지 모르겠다면?",
                style = MooditTheme.typography.h1,
                color = MooditTheme.colors.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "저장한 사진은 수백 장인데 막상 내 취향을\n설명하기 어려웠다면 무딧이 도와줄게요",
                style = MooditTheme.typography.b2Small,
                color = MooditTheme.colors.textSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun SaveTasteScreenPreview() {
    MooditTheme {
        SaveTasteScreen(onNextClick = {})
    }
}
