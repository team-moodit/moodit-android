package com.swyp.moodit.tournament.component

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Candidate

@Composable
fun MoodCandidateItem(
    isSelected: Boolean,
    anyPhotoSelected: Boolean,
    moodCandidate: Candidate,
    modifier: Modifier = Modifier
) {
    val borderModifier = if (isSelected) Modifier.border(
        width = 2.dp,
        color = MooditTheme.colors.primary,
        shape = RoundedCornerShape(16.dp)
    ) else Modifier

    val photoModifier = if (!isSelected && anyPhotoSelected) Modifier
        .blur(radiusX = 16.dp, radiusY = 16.dp)
        .drawWithContent {
            drawContent()
            drawRect(color = Color.Black.copy(alpha = 0.5f))
        } else Modifier

    AsyncImage(
        model = moodCandidate.photoUri,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .then(borderModifier)
            .then(photoModifier),
        contentDescription = "img_candidate",
        contentScale = ContentScale.Crop,
        alpha = if (anyPhotoSelected && isSelected.not()) 0.5f else 1f
    )
}


@Composable
@Preview
fun MoodCandidateItemPreview() {
    MaterialTheme {
        MoodCandidateItem(
            isSelected = true,
            anyPhotoSelected = true,
            moodCandidate = Candidate(
                id = 1,
                photoUri = "https://example.com/test.jpg"
            )
        )
    }
}