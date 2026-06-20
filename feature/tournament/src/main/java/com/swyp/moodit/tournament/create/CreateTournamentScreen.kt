package com.swyp.moodit.tournament.create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.tournament.component.UploadPhotoItem
import com.swyp.moodit.tournament.component.UploadPhotoStatusCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTournamentScreen(
    onCreateTournamentClick: () -> Unit,
    onPhotoPickerClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDeletePhotoClick: (String) -> Unit,
    onRetryUploadClick: (SelectedPhoto) -> Unit,
    uiState: CreateTournamentContract.State
) {
    val containerColor = Color(0xFF12141B)
    val cardBackGroundColor = Color(0xFF1A1B23)
    val primaryContainerColor = Color(0xFFC4F768)
    val onPrimaryContainerColor = Color(0xFF000000)
    val disabledContainerColor = Color(0xFF232428)
    val disabledContentColor = Color(0xFF4A4B5A)
    val badgeColor = Color(0xFF000000)
    val strokeColor = Color(0xFF6F768C)
    val textColor = Color(0xFFFFFFFF)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = containerColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "새 무드매치 만들기",
                        fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121318))
            )
        }, bottomBar = {
            Button(
                onClick = { onCreateTournamentClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryContainerColor,
                    contentColor = onPrimaryContainerColor,
                    disabledContainerColor = disabledContainerColor,
                    disabledContentColor = disabledContentColor
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = uiState.isTournamentValid
            ) {
                Text(text = if (uiState.isTournamentValid) "시작하기" else "사진 준비 중")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "무드매치 제목",
                color = textColor,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 14.sp
            )
            OutlinedTextField(
                value = uiState.title,
                onValueChange = {
                    if (it.length <= 20) onTitleChange(it)
                },
                placeholder = { Text("ex) 봄에 따라 입고 싶은 룩", color = Color.Gray, fontSize = 14.sp) },
                trailingIcon = {
                    if (uiState.title.isNotEmpty()) {
                        IconButton(onClick = { onTitleChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "지우기", tint = Color.Gray)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.DarkGray,
                    focusedContainerColor = cardBackGroundColor,
                    unfocusedContainerColor = cardBackGroundColor,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Text(
                text = "${uiState.title.length}/20",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 4.dp),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(24.dp))

            UploadPhotoStatusCard(uiState)

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(cardBackGroundColor, RoundedCornerShape(12.dp))
                            .border(1.dp, strokeColor, RoundedCornerShape(12.dp))
                            .clickable { onPhotoPickerClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_input_add),
                            contentDescription = "icon_insert_photo",
                            tint = badgeColor,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                itemsIndexed(uiState.selectedPhotos) { index, photo ->
                    UploadPhotoItem(
                        onDeleteClick = { onDeletePhotoClick(photo.id) },
                        onRetryClick = { onRetryUploadClick(photo) },
                        photo = photo
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun CreateTournamentScreenPreview() {
    MaterialTheme {
        CreateTournamentScreen(
            uiState = CreateTournamentContract.State(
                title = "무드매치 제목"
            ),
            onTitleChange = {},
            onPhotoPickerClick = {},
            onDeletePhotoClick = {},
            onRetryUploadClick = {},
            onCreateTournamentClick = {}
        )
    }
}