package com.swyp.moodit.designsystem.component.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MooditFilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    containerColor: Color = MooditTheme.colors.primary,
    contentColor: Color = MooditTheme.colors.onPrimary,
    disabledContainerColor: Color = MooditTheme.colors.surfaceContainer,
    disabledContentColor: Color = MooditTheme.colors.onSurfaceContainer,
    pressedContainerColor: Color? = null,
    pressedContentColor: Color? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val colors = ButtonDefaults.buttonColors(
        containerColor = if (isPressed) {
            pressedContainerColor ?: containerColor.copy(alpha = 0.8f)
        } else {
            containerColor
        },
        contentColor = if (isPressed) {
            pressedContentColor ?: contentColor.copy(alpha = 0.8f)
        } else {
            contentColor
        },
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor
    )

    Button(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        onClick = onClick,
        interactionSource = interactionSource,
        colors = colors,
        shape = shape,
        enabled = enabled
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
fun MooditFilledButtonPreview() {
    MooditTheme {
        MooditFilledButton(text = "Button", enabled = true)
    }
}