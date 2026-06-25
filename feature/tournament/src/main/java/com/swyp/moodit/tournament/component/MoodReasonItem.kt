package com.swyp.moodit.tournament.component

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
import com.swyp.moodit.tournament.matchUp.MoodReason

@Composable
fun MoodReasonItem(reason: MoodReason, onReasonClick: () -> Unit) {
    val baseModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .clickable { onReasonClick() }

    val borderModifier = if (reason.isSelected) {
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
            text = reason.content,
            style = MooditTheme.typography.b2Medium,
            color = MooditTheme.colors.onBackground
        )
        if (reason.isSelected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "icon_check_reason",
                tint = MooditTheme.colors.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
@Preview
fun MoodReasonItemPreview() {
    val testReason = MoodReason(content = "나한테 잘 어울릴 것 같아서", isSelected = true)
    MaterialTheme {
        MoodReasonItem(reason = testReason, onReasonClick = {})
    }
}