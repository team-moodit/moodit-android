package com.swyp.moodit.home.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditDialog
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.home.BuildConfig


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onTermsClick: () -> Unit,
    onNicknameClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onFeedbackClick: () -> Unit,
    onShowLogOutDialog: () -> Unit,
    onShowDeleteAccountDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onConfirmLogOutClick: () -> Unit,
    onConfirmDeleteAccountClick: () -> Unit,
    onConfirmCompleteDeleteAccountClick: () -> Unit,
    uiState: SettingContract.State
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MooditTopBar(
                title = {
                    Text(
                        text = "설정",
                        style = MooditTheme.typography.h2
                    )
                },
                textAlign = TextAlign.Center
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .clickable { onShowDeleteAccountDialog() }
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "계정 삭제",
                    color = MooditTheme.colors.error,
                    style = MooditTheme.typography.b3Medium
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = MooditTheme.colors.error
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "카카오 계정 정보",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b3Medium
                )
                Text(
                    text = uiState.email,
                    color = MooditTheme.colors.textSecondary,
                    style = MooditTheme.typography.b3Medium
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNicknameClick() }
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "닉네임",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b3Medium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        text = uiState.name,
                        color = MooditTheme.colors.textSecondary,
                        style = MooditTheme.typography.b3Medium
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "icon_click",
                        modifier = Modifier.size(20.dp),
                        tint = MooditTheme.colors.onTertiary
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 12.dp,
                color = MooditTheme.colors.onPrimary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTermsClick() }
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "이용약관",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b3Medium
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = MooditTheme.colors.onTertiary
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPrivacyPolicyClick() }
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "개인정보 처리방침",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b3Medium
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = MooditTheme.colors.onTertiary
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "버전 정보",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b3Medium
                )
                Text(
                    text = BuildConfig.VERSION_NAME,
                    color = MooditTheme.colors.textSecondary,
                    style = MooditTheme.typography.b3Medium
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFeedbackClick() }
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "피드백 하기",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b3Medium
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = MooditTheme.colors.onTertiary
                )
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 12.dp,
                color = MooditTheme.colors.onPrimary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShowLogOutDialog() }
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "로그아웃",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b3Medium
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = MooditTheme.colors.onTertiary
                )
            }

            uiState.dialogType?.let { dialogType ->
                when (dialogType) {
                    SettingContract.DialogType.LOGOUT -> {
                        MooditDialog(
                            title = "로그아웃",
                            description = "로그아웃 하시겠습니까?",
                            onClickCancel = { onDismissDialog() }
                        ) {
                            MooditFilledButton(
                                onClick = { onDismissDialog() },
                                modifier = Modifier.weight(1f),
                                text = "취소",
                                containerColor = MooditTheme.colors.surfaceContainer,
                                contentColor = MooditTheme.colors.textSecondary
                            )
                            MooditFilledButton(
                                onClick = {
                                    onConfirmLogOutClick()
                                },
                                modifier = Modifier.weight(1f),
                                text = "확인"
                            )
                        }
                    }

                    SettingContract.DialogType.DELETE_ACCOUNT -> {
                        MooditDialog(
                            title = "정말 탈퇴하시겠어요?",
                            description = "모든 기록이 삭제되며 복구할 수 없어요.",
                            onClickCancel = { onDismissDialog() }
                        ) {
                            MooditFilledButton(
                                onClick = { onDismissDialog() },
                                modifier = Modifier.weight(1f),
                                text = "취소",
                                containerColor = MooditTheme.colors.primary.copy(alpha = 0.1f),
                                contentColor = MooditTheme.colors.primary
                            )
                            MooditFilledButton(
                                onClick = {
                                    onConfirmDeleteAccountClick()
                                },
                                modifier = Modifier.weight(1f),
                                text = "확인"
                            )
                        }
                    }

                    SettingContract.DialogType.COMPLETE_DELETE_ACCOUNT -> {
                        MooditDialog(
                            title = "탈퇴가 완료되었어요",
                            description = "그동안 무딧을 이용해주셔서 감사해요.",
                            onClickCancel = { onConfirmCompleteDeleteAccountClick() },
                            icon = {
                                Image(
                                    modifier = Modifier.size(80.dp),
                                    painter = painterResource(R.drawable.complete),
                                    contentDescription = "icon_delete_account_complete"
                                )
                            }
                        ) {
                            MooditFilledButton(
                                onClick = {
                                    onConfirmCompleteDeleteAccountClick()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                text = "확인"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SettingScreenPreview() {
    MooditTheme {
        SettingScreen(
            onTermsClick = {},
            onNicknameClick = {},
            onPrivacyPolicyClick = {},
            onFeedbackClick = {},
            onShowLogOutDialog = {},
            onShowDeleteAccountDialog = {},
            onDismissDialog = {},
            onConfirmLogOutClick = {},
            onConfirmDeleteAccountClick = {},
            onConfirmCompleteDeleteAccountClick = {},
            uiState = SettingContract.State()
        )
    }
}