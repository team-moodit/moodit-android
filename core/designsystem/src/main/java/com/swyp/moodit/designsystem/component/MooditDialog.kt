package com.swyp.moodit.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MooditDialog(
    onClickCancel: () -> Unit = {},
    title: String,
    description: String,
    icon: @Composable (() -> Unit)? = null,
    buttons: @Composable RowScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onClickCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MooditTheme.colors.primaryContainer,
                contentColor = MooditTheme.colors.surfaceContainer
            ),
            border = BorderStroke(2.dp, MooditTheme.colors.surfaceContainer)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (icon != null) {
                    icon()
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Text(
                    text = title,
                    style = MooditTheme.typography.b1Large,
                    textAlign = TextAlign.Center,
                    color = MooditTheme.colors.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = description,
                    style = MooditTheme.typography.b2Medium,
                    color = MooditTheme.colors.textSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    buttons()
                }
            }
        }
    }
}

@Preview
@Composable
fun MooditDialogPreview() {
    MooditTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MooditDialog(
                onClickCancel = {},
                title = "로그아웃",
                description = "로그아웃 하시겠습니까?",
                buttons = {
                    MooditFilledButton(
                        modifier = Modifier.weight(1f),
                        text = "취소",
                        enabled = false,
                        disabledContainerColor = MooditTheme.colors.surfaceContainer,
                        disabledContentColor = MooditTheme.colors.textSecondary
                    )
                    MooditFilledButton(
                        modifier = Modifier.weight(1f),
                        text = "확인"
                    )
                }
            )
        }
    }
}