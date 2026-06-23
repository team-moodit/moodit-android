package com.swyp.moodit.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MooditSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    iconResId: Int = R.drawable.star_filled,
    iconColor: Color = MooditTheme.colors.primary,
    textColor: Color = MooditTheme.colors.tertiary
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .background(
                MooditTheme.colors.surfaceContainer,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = "icon_snackbar",
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = snackbarData.visuals.message,
            color = textColor,
            style = MooditTheme.typography.b2Medium
        )
    }
}

@Preview
@Composable
fun MooditSnackbarPreview() {
    val mockSnackbarData = object : SnackbarData {
        override val visuals: SnackbarVisuals = object : SnackbarVisuals {
            override val message: String = "진행 상황이 저장됐어요"
            override val actionLabel: String? = null // 필요한 경우 "Action" 대입
            override val duration: SnackbarDuration = SnackbarDuration.Short
            override val withDismissAction: Boolean = false
        }

        // 프리뷰에서는 사용하지 않는 인터랙션 메서드들은 빈 채로 둡니다.
        override fun dismiss() {}
        override fun performAction() {}
    }

    MooditTheme {
        MooditSnackbar(snackbarData = mockSnackbarData)
    }
}