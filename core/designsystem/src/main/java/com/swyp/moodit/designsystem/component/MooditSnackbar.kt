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
    successIconResId: Int = R.drawable.star_filled,
    errorIconResId: Int = R.drawable.info_triangle,
    successIconColor: Color = MooditTheme.colors.primary,
    errorIconColor: Color = MooditTheme.colors.onSurface,
    textColor: Color = MooditTheme.colors.tertiary
) {
    val visuals = snackbarData.visuals as? MooditSnackbarVisuals
    val type = visuals?.type ?: MooditSnackbarType.SUCCESS

    val iconId = when (type) {
        MooditSnackbarType.SUCCESS -> successIconResId
        MooditSnackbarType.ERROR -> errorIconResId
    }
    val iconColor = when (type) {
        MooditSnackbarType.SUCCESS -> successIconColor
        MooditSnackbarType.ERROR -> errorIconColor
    }

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
            painter = painterResource(iconId),
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
            override val actionLabel: String? = null
            override val duration: SnackbarDuration = SnackbarDuration.Short
            override val withDismissAction: Boolean = false
        }

        override fun dismiss() {}
        override fun performAction() {}
    }

    MooditTheme {
        MooditSnackbar(snackbarData = mockSnackbarData)
    }
}

enum class MooditSnackbarType {
    SUCCESS, ERROR
}

data class MooditSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val type: MooditSnackbarType = MooditSnackbarType.SUCCESS
) : SnackbarVisuals