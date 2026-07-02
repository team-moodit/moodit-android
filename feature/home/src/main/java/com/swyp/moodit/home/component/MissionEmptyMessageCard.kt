package com.swyp.moodit.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.home.R
import com.swyp.moodit.model.MissionState

@Composable
fun MissionEmptyMessageCard(modifier: Modifier = Modifier, missionState: MissionState) {
    val message = when (missionState) {
        MissionState.IN_PROGRESS -> Pair(
            R.string.IN_PROGRESS_EMPTY_MISSION_TITLE_MESSAGE,
            R.string.IN_PROGRESS_EMPTY_MISSION_MESSAGE
        )

        MissionState.COMPLETED -> Pair(
            R.string.COMPLETED_EMPTY_MISSION_TITLE_MESSAGE,
            R.string.COMPLETED_EMPTY_MISSION_MESSAGE
        )

        MissionState.REVIEWED -> Pair(
            R.string.FEEDBACK_SUBMITTED_EMPTY_MISSION_TITLE_MESSAGE,
            R.string.FEEDBACK_SUBMITTED_EMPTY_MISSION_MESSAGE
        )
    }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MooditTheme.colors.onPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(message.first),
                color = MooditTheme.colors.primary,
                style = MooditTheme.typography.h4
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(message.second),
                color = MooditTheme.colors.tertiary,
                style = MooditTheme.typography.b2ExtraSmall
            )
        }
    }
}

@Preview
@Composable
fun MissionEmptyMessageCardPreview() {
    MooditTheme {
        MissionEmptyMessageCard(missionState = MissionState.IN_PROGRESS)
    }
}