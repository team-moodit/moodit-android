package com.swyp.moodit.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun LoginScreen(
    onKakaoLoginClick: () -> Unit
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Button(
                onClick = { onKakaoLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDE500),
                    contentColor = MooditTheme.colors.primaryContainer
                ),
                contentPadding = PaddingValues(0.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.kakao_talk_logo),
                        modifier = Modifier.size(16.dp),
                        contentDescription = "kakao_logo"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "카카오로 시작하기",
                        style = MooditTheme.typography.b1Medium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

        }
    ) { }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.72f))
        Image(
            painter = painterResource(R.drawable.moodit_logo),
            modifier = Modifier.size(60.dp).padding(bottom = 6.dp),
            contentDescription = "moodit_logo"
        )
        Image(
            painter = painterResource(R.drawable.moodit_logo_title),
            modifier = Modifier.width(150.dp),
            contentDescription = "moodit_logo"
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "사진을 취향으로, 취향을 행동으로, \n" +
                    "행동을 확신으로",
            style = MooditTheme.typography.b2Small,
            color = MooditTheme.colors.textSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    MooditTheme {
        LoginScreen(onKakaoLoginClick = {})
    }
}