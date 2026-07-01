package com.swyp.moodit.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MooditSelectableButton(content: String, isSelected: Boolean, onItemClick: () -> Unit) {
    val baseModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .clickable { onItemClick() }

    val borderModifier = if (isSelected) {
        Modifier
            .background(MooditTheme.colors.primary.copy(alpha = 0.1f))
            .border(1.dp, MooditTheme.colors.primary, RoundedCornerShape(12.dp))
    } else {
        Modifier
            .background(MooditTheme.colors.primaryContainer)
            .border(1.dp, MooditTheme.colors.surfaceContainer, RoundedCornerShape(12.dp))
    }

    Row(
        modifier = baseModifier
            .then(borderModifier)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = content,
            style = MooditTheme.typography.b2Small,
            color = MooditTheme.colors.onBackground
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "icon_check_reason",
                tint = MooditTheme.colors.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
@Preview
fun MooditSelectableButtonPreview() {
    val testContent = "나한테 잘 어울릴 것 같아서"
    MaterialTheme {
        MooditSelectableButton(
            content = testContent,
            isSelected = true,
            onItemClick = {}
        )
    }
}