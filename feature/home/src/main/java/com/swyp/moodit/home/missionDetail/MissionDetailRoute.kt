package com.swyp.moodit.home.missionDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditLottie
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.MissionDetailLoadingType

@Composable
fun MissionDetailRoute(
    viewModel: MissionDetailViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToReportReady: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is MissionDetailContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )

                is MissionDetailContract.SideEffect.NavigateToReportReady -> navigateToReportReady()
                is MissionDetailContract.SideEffect.NavigateToHome -> navigateToHome()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(MissionDetailContract.Intent.LoadMissionDetail)
        viewModel.sendIntent(MissionDetailContract.Intent.LoadUserInfo)
    }

    BackHandler {
        viewModel.sendIntent(MissionDetailContract.Intent.OnExitClick)
    }

    when (uiState.isLoading) {
        MissionDetailLoadingType.DEFAULT -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        MissionDetailLoadingType.REPORT -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(64.dp))
                    Surface(
                        shape = RoundedCornerShape(100.dp),
                        color = MooditTheme.colors.surfaceContainer,
                        modifier = Modifier.padding(bottom = 32.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.diagnosis_fill),
                                contentDescription = null,
                                tint = MooditTheme.colors.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "방금 남긴 경험이 리포트에 반영돼요",
                                color = MooditTheme.colors.tertiary,
                                style = MooditTheme.typography.b3Medium
                            )
                        }
                    }

                    Text(
                        text = "${uiState.nickname}님의 취향을\n더 선명하게 정리하고 있어요",
                        style = MooditTheme.typography.h1,
                        color = MooditTheme.colors.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(150.dp))

                    MooditLottie()
                    Spacer(modifier = Modifier.height(110.dp))
                }
            }
        }

        MissionDetailLoadingType.NONE -> {
            MissionDetailScreen(
                uiState = uiState,
                onTryButtonClick = { viewModel.sendIntent(MissionDetailContract.Intent.OnTryButtonClick) },
                onCompleteClick = { viewModel.sendIntent(MissionDetailContract.Intent.OnCompleteClick) },
                onDeleteCompleteClick = { viewModel.sendIntent(MissionDetailContract.Intent.OnDeleteCompleteClick) },
                onDeleteClick = { viewModel.sendIntent(MissionDetailContract.Intent.OnDeleteClick) },
                onSatisfactionShowChange = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.OnSatisfactionShowChange(
                            it
                        )
                    )
                },
                onFeedbackShowChange = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.OnFeedbackShowChange(
                            it
                        )
                    )
                },
                onSliderRatingChange = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.OnSliderRatingChange(
                            it
                        )
                    )
                },
                onToggleFeedbackOption = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.ToggleFeedbackOption(
                            it
                        )
                    )
                },
                onDeleteDialogShowChange = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.OnDeleteDialogShowChange(
                            it
                        )
                    )
                },
                onDeleteCompleteDialogShowChange = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.OnDeleteCompleteDialogShowChange(
                            it
                        )
                    )
                },
                submitSatisfaction = { viewModel.sendIntent(MissionDetailContract.Intent.SubmitSatisfaction) },
                onClearFeedbackOption = { viewModel.sendIntent(MissionDetailContract.Intent.ClearFeedbackOption) }
            )
        }
    }
}

@Preview
@Composable
fun MissionDetailRoutePreview() {
    MooditTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    color = MooditTheme.colors.surfaceContainer,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.diagnosis_fill),
                            contentDescription = null,
                            tint = MooditTheme.colors.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "방금 남긴 경험이 리포트에 반영돼요",
                            color = MooditTheme.colors.tertiary,
                            style = MooditTheme.typography.b3Medium
                        )
                    }
                }

                Text(
                    text = "비비님의 취향을\n더 선명하게 정리하고 있어요",
                    style = MooditTheme.typography.h1,
                    color = MooditTheme.colors.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(150.dp))

                MooditLottie()
                Spacer(modifier = Modifier.height(110.dp))
            }
        }
    }
}