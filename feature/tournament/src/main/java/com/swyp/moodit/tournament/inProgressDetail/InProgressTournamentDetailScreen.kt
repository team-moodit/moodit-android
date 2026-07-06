package com.swyp.moodit.tournament.inProgressDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTag
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InProgressTournamentDetailScreen(
    uiState: InProgressTournamentDetailContract.State,
    onDeleteClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    MooditScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MooditTopBar(
                title = {
                    Text(
                        text = "진행중인 무드매치",
                        style = MooditTheme.typography.h3
                    )
                },
                textAlign = TextAlign.Center,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MooditFilledButton(modifier = Modifier.weight(1f), text = "삭제하기")
                MooditFilledButton(modifier = Modifier.weight(1f), text = "이어서 진행하기")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InProgressTournamentContent(onDeleteClick = onDeleteClick)
        }
    }
}

@Composable
fun InProgressTournamentContent(
    onDeleteClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MooditTag(content = "7일 전 마지막 진행")
        Text(text = "16강 중 4강까지 진행한 무드매치에요\n계속 이어서 진행할까요?")
        Text(text = "무드매치 정보")
        Card(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "무드매치 이름")
                    Text(text = "약속날 입고 갈 옷 토너먼트")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "무드매치 이름")
                    Text(text = "약속날 입고 갈 옷 토너먼트")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "진행률")
                    Text(text = "4강 / 16강")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "마지막 진행 날짜")
                    Text(text = "26.06.09")
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "선택한 사진")
            MooditTag(content = "16장")
        }

        /*LazyVerticalGrid(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(bottom = 6.dp), columns = GridCells.Fixed(3)) {
            items(count = )
        }*/
    }
}

