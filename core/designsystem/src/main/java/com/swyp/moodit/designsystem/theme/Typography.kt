package com.swyp.moodit.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.swyp.moodit.designsystem.R

val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard_black, FontWeight.Black),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
    Font(R.font.pretendard_extralight, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_thin, FontWeight.Thin)
)

@Immutable
data class MooditTextStyle(
    val fontFamily: FontFamily = pretendardFontFamily,
    val fontWeight: FontWeight = FontWeight.Medium,
    val fontSize: Dp = Dp.Unspecified,
    val lineHeight: Dp = Dp.Unspecified,
    val letterSpacing: Dp = Dp.Unspecified
)
