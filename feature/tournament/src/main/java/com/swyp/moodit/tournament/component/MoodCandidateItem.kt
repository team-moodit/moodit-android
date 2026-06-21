package com.swyp.moodit.tournament.component

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.tournament.matchUp.MoodCandidate

@Composable
fun MoodCandidateItem(
    isSelected: Boolean,
    anyPhotoSelected: Boolean,
    moodCandidate: MoodCandidate,
    modifier: Modifier = Modifier
) {
    val borderModifier = if (isSelected) Modifier.border(
        width = 2.dp,
        color = Color.Red,
        shape = RoundedCornerShape(12.dp)
    ) else Modifier

    AsyncImage(
        model = moodCandidate.photoUri,
        modifier = modifier.clip(RoundedCornerShape(16.dp)).then(borderModifier),
        contentDescription = "img_candidate",
        contentScale = ContentScale.Crop,
        alpha = if (anyPhotoSelected && isSelected.not()) 0.5f else 1f,
        error = ColorPainter(Color.Yellow)
    )
}


@Composable
@Preview
fun MoodCandidateItemPreview() {
    MaterialTheme {
        MoodCandidateItem(
            isSelected = true,
            anyPhotoSelected = true,
            moodCandidate = MoodCandidate(
                id = 1,
                name = "Test Candidate",
                photoUri = "https://example.com/test.jpg"
            )
        )
    }
}