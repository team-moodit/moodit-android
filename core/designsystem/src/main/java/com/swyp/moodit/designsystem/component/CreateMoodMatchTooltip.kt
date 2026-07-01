package com.swyp.moodit.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.rememberBalloonState
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun CreateMoodMatchTooltip() {
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setMarginHorizontal(12)
        setPadding(12)
        setCornerRadius(16f)
        setBackgroundColor(Color(0xFF1D212A).toArgb())
        setBalloonAnimation(BalloonAnimation.ELASTIC)
    }

    val balloonState = rememberBalloonState(builder)

    Icon(
        imageVector = Icons.Default.Info,
        contentDescription = "info",
        tint = MooditTheme.colors.borderDefault,
        modifier = Modifier
            .size(20.dp)
            .balloon(balloonState) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(vertical = 16.dp),
                    text = "제목은 사진을 고를 때마다 떠올릴 질문이에요.\n구체적으로 쓸수록 고르기 쉬워져요.",
                    style = MooditTheme.typography.caption,
                    color = MooditTheme.colors.primary,
                    textAlign = TextAlign.Start
                )
            }
            .clickable { balloonState.showAlignBottom() }
    )
}

@Preview
@Composable
fun CreateMoodMatchTooltipPreview() {
    MooditTheme {
        CreateMoodMatchTooltip()
    }
}

