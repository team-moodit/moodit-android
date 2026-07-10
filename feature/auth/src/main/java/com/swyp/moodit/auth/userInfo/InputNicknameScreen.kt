package com.swyp.moodit.auth.userInfo

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputNicknameScreen(
    uiState: InputNicknameContract.State,
    onNicknameChange: (String) -> Unit,
    onConfirmClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (uiState.isEditMode) {
                MooditTopBar(
                    title = {
                        Text(
                            text = "닉네임",
                            style = MooditTheme.typography.h2,
                            color = MooditTheme.colors.onBackground
                        )
                    },
                    textAlign = TextAlign.Center
                )
            }
        },
        bottomBar = {
            MooditFilledButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { onConfirmClick() },
                enabled = uiState.isNicknameValid,
                text = "확인"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            if (uiState.isEditMode) {
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "어떻게 불러드리는 게\n좋을까요?",
                    style = MooditTheme.typography.h1,
                    color = MooditTheme.colors.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = if (uiState.isEditMode) "새로운 닉네임을 입력해주세요" else "닉네임",
                style = MooditTheme.typography.b2Small,
                color = MooditTheme.colors.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.nickname,
                onValueChange = { onNicknameChange(it) },
                trailingIcon = {
                    if (uiState.nickname.isNotEmpty()) {
                        IconButton(
                            onClick = { onNicknameChange("") },
                            modifier = Modifier
                                .padding(4.dp)
                                .size(24.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MooditTheme.colors.onSurfaceContainer,
                                contentColor = MooditTheme.colors.onTertiary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "icon_delete",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MooditTheme.colors.borderDefault,
                    unfocusedBorderColor = MooditTheme.colors.borderDefault,
                    focusedContainerColor = MooditTheme.colors.onPrimary,
                    unfocusedContainerColor = MooditTheme.colors.onPrimary,
                    focusedTextColor = MooditTheme.colors.onBackground,
                    unfocusedTextColor = MooditTheme.colors.onBackground,
                    cursorColor = MooditTheme.colors.onBackground,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "한글 최대 6자까지 입력 가능해요.",
                    style = MooditTheme.typography.b3Small,
                    color = MooditTheme.colors.primary
                )
                Text(
                    text = "${uiState.nickname.length}/6",
                    style = MooditTheme.typography.b3Small,
                    color = MooditTheme.colors.borderDefault
                )
            }
        }
    }
}

@Preview
@Composable
fun InputNicknameScreenPreview() {
    MooditTheme {
        InputNicknameScreen(
            uiState = InputNicknameContract.State(nickname = "123"),
            onNicknameChange = {},
            onConfirmClick = {}
        )
    }
}