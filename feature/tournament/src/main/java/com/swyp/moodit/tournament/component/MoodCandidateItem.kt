package com.swyp.moodit.tournament.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Candidate
import com.swyp.moodit.ui.shimmerEffect

@Composable
fun MoodCandidateItem(
    isSelected: Boolean,
    anyPhotoSelected: Boolean,
    moodCandidate: Candidate,
    onItemClick: () -> Unit = {},
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

    SubcomposeAsyncImage(
        model = moodCandidate.photoUri,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .then(borderModifier)
            .clickable { onItemClick() },
        contentDescription = "img_candidate",
        contentScale = ContentScale.Crop,
        alpha = if (anyPhotoSelected && isSelected.not()) 0.5f else 1f,
        success = {
            SubcomposeAsyncImageContent(
                modifier = Modifier
                    .fillMaxSize()
                    .then(photoModifier)
            )
        },
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerEffect()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {}
            )
        },

        error = {

        }
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