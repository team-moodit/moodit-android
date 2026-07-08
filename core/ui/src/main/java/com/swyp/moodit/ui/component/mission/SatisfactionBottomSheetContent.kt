package com.swyp.moodit.ui.component.mission

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.R
import com.swyp.moodit.designsystem.component.MooditSlider
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun SatisfactionBottomSheetContent(
    currentSliderRating: Float,
    onValueChange: (Float) -> Unit,
    onCompleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "직접 미션을 해보니 어땠나요?",
            style = MooditTheme.typography.h2,
            color = MooditTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .border(
                    1.dp,
                    MooditTheme.colors.onSurfaceContainer,
                    RoundedCornerShape(999.dp)
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(),
                contentDescription = "icon_info",
                modifier = Modifier.size(16.dp),
                tint = MooditTheme.colors.onTertiary
            )
            Text(
                text = "탭하거나 드래그해서 0.5점까지 선택할 수 있어요",
                style = MooditTheme.typography.b3Small,
                color = MooditTheme.colors.textSecondary
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        MooditSlider(
            value = currentSliderRating,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "1.0",
                style = MooditTheme.typography.caption,
                color = MooditTheme.colors.tertiary
            )
            Text(
                text = "5.0",
                style = MooditTheme.typography.caption,
                color = MooditTheme.colors.tertiary
            )
        }
        val satisfactionText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MooditTheme.colors.primary,
                    fontFamily = MooditTheme.typography.h1.fontFamily,
                    fontStyle = MooditTheme.typography.h1.fontStyle,
                    fontSize = MooditTheme.typography.h1.fontSize,
                    fontWeight = MooditTheme.typography.h1.fontWeight,
                    letterSpacing = MooditTheme.typography.h1.letterSpacing
                )
            ) {
                append("$currentSliderRating")
            }

            withStyle(
                style = SpanStyle(
                    color = MooditTheme.colors.textSecondary,
                    fontFamily = MooditTheme.typography.b3Medium.fontFamily,
                    fontStyle = MooditTheme.typography.b3Medium.fontStyle,
                    fontSize = MooditTheme.typography.b3Medium.fontSize,
                    fontWeight = MooditTheme.typography.b3Medium.fontWeight
                )
            ) {
                append(" / 5.0")
            }
        }

        Text(text = satisfactionText)

        Spacer(modifier = Modifier.height(24.dp))

        MooditFilledButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onCompleteClick() },
            enabled = currentSliderRating != 0.0f,
            text = "완료"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF12141B)
@Composable
fun SatisfactionBottomSheetContentPreview() {
    MooditTheme {
        SatisfactionBottomSheetContent(
            currentSliderRating = 1.0f,
            onValueChange = {},
            onCompleteClick = {}
        )
    }
}