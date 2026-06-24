package com.swyp.moodit.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MooditSelectedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    isSelected: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
    strokeWidth: Dp = 1.dp,
    strokeColor: Color = MooditTheme.colors.primary,
    unSelectedStrokeColor: Color = MooditTheme.colors.surfaceContainer,
    containerColor: Color = MooditTheme.colors.primary.copy(alpha = 0.1f),
    unSelectedContainerColor: Color = MooditTheme.colors.surfaceContainer,
    contentColor: Color = MooditTheme.colors.primary,
    unSelectedContentColor: Color = MooditTheme.colors.onSurfaceContainer,
) {
    val containerColor = if (isSelected) containerColor else unSelectedContainerColor
    val contentColor = if (isSelected) contentColor else unSelectedContentColor
    val strokeColor = if (isSelected) strokeColor else unSelectedStrokeColor

    val colors = ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor
    )

    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = shape,
        colors = colors,
        border = BorderStroke(strokeWidth, strokeColor),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = text,
            style = MooditTheme.typography.b1Medium
        )
    }
}

@Preview
@Composable
fun MooditSelectedButtonPreview() {
    MooditTheme {
        MooditSelectedButton(text = "Button")
    }
}