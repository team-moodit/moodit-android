package com.swyp.moodit.tournament.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.tournament.matchUp.MoodReason

@Composable
fun MoodReasonItem(reason: MoodReason, onReasonClick: () -> Unit) {
    var baseModifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { onReasonClick() }

    if (reason.isSelected) {
        baseModifier = baseModifier
            .background(Color.Green)
            .border(1.dp, Color.Yellow, RoundedCornerShape(16.dp))
    }

    Row(
        modifier = baseModifier
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = reason.content)
        if (reason.isSelected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "icon_check_reason",
                tint = Color.Red,
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