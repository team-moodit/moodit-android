package com.swyp.moodit.report.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun ReportGuideDescription(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(R.drawable.reward_stars),
            modifier = Modifier.size(24.dp),
            contentDescription = "icon_star",
            tint = MooditTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "계속해서 만족도를 쌓아나가면\n취향의 기준이 보여요!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MooditTheme.typography.b1Medium,
            color = MooditTheme.colors.onPrimaryContainer
        )
    }
}

@Preview
@Composable
fun ReportGuideDescriptionPreview() {
    MooditTheme {
        ReportGuideDescription()
    }
}