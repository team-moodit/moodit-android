package com.swyp.moodit.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R

@Composable
fun EmptyImageRow() {
    val imageArray = intArrayOf(
        R.drawable.empty1,
        R.drawable.empty2,
        R.drawable.empty3
    )

    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            imageArray.forEachIndexed { index, url ->
                Image(
                    painter = painterResource(url),
                    contentDescription = "empty_image",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(188f / 222f)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
    }
}

@Preview
@Composable
fun EmptyImageRowPreview() {
    EmptyImageRow()
}