package com.swyp.moodit.home.reportReady

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
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

@Composable
fun ReportReadyScreen(
    onNavigateReportClick: () -> Unit,
    onNavigateHomeClick: () -> Unit
) {
    MooditScaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(164.dp))
            Text(
                text = "리포트가 업데이트됐어요\n지금 확인해보세요",
                style = MooditTheme.typography.h1,
                color = MooditTheme.colors.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(110.dp))

            Image(
                painter = painterResource(R.drawable.icon),
                modifier = Modifier.size(120.dp),
                contentDescription = "icon_check"
            )

            Spacer(modifier = Modifier.weight(1f))

            MooditFilledButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigateReportClick() },
                text = "리포트 보러가기"
            )

            Spacer(modifier = Modifier.height(16.dp))

            MooditFilledButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigateHomeClick() },
                text = "홈으로 가기",
                containerColor = MooditTheme.colors.surfaceContainer,
                contentColor = MooditTheme.colors.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun ReportReadyScreenPreview() {
    MooditTheme {
        ReportReadyScreen(
            onNavigateReportClick = {},
            onNavigateHomeClick = {}
        )
    }
}