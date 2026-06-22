package com.swyp.moodit.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
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
    val fontSize: TextUnit = TextUnit.Unspecified,
    val lineHeight: TextUnit = TextUnit.Unspecified,
    val letterSpacing: TextUnit = TextUnit.Unspecified
)

@Immutable
data class MooditTypography(
    val h1: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.48).sp
    ),

    val h2: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.4).sp
    ),

    val h3: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    val h4: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),

    val b1Large: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    val b1Medium: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.32).sp
    ),

    val b1Small: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.28).sp
    ),

    val b2Large: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    val b2Medium: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    val b2Small: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    val b2ExtraSmall: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    val b3Large: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    val b3Medium: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    val b3Small: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    val caption: MooditTextStyle = MooditTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    )
)

val AppTypography = MooditTypography()
