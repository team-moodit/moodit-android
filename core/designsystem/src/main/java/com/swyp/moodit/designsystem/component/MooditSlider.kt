package com.swyp.moodit.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme
import kotlin.math.roundToInt

@Composable
fun MooditSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    activeColor: Color = MooditTheme.colors.primary,
    inactiveColor: Color = MooditTheme.colors.onSurface
) {
    val currentOnValueChange by rememberUpdatedState(onValueChange)

    val minRating = 1.0f
    val maxRating = 5.0f
    val steps = 8 // (5.0 - 1.0) / 0.5 = 8개의 구간

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val calculatedValue = calculateValueFromX(offset.x, size.width, minRating, maxRating, steps)
                    currentOnValueChange(calculatedValue)
                }
            }

            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, _ ->
                    change.consume()
                    val calculatedValue = calculateValueFromX(change.position.x, size.width, minRating, maxRating, steps)
                    currentOnValueChange(calculatedValue)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxWidth()) {
            val width = size.width
            val centerY = size.height / 2
            val totalPoints = steps + 1
            val spacing = width / steps

            // 배경선
            drawLine(
                color = inactiveColor,
                start = Offset(0f, centerY),
                end = Offset(width, centerY),
                strokeWidth = 3.dp.toPx()
            )

            val activeProgressFraction = (value - minRating) / (maxRating - minRating)
            val activeEndX = width * activeProgressFraction
            if (activeEndX > 0) {
                drawLine(
                    color = activeColor,
                    start = Offset(0f, centerY),
                    end = Offset(activeEndX, centerY),
                    strokeWidth = 3.dp.toPx()
                )
            }

            // 각 단계별 모양 그리기
            for (i in 0 until totalPoints) {
                val currentPointValue = minRating + (i * 0.5f)
                val pointX = i * spacing
                val isPointActive = currentPointValue <= value
                val color = if (isPointActive) activeColor else inactiveColor

                if (i % 2 == 0) {
                    // 정수 모양 그리기 (휘어져 있는 다이아몬드)
                    drawDiamondPoint(x = pointX, centerY = centerY, sizePx = 24.dp.toPx(), color = color)
                } else {
                    // 중간 원 그리기
                    drawCircle(
                        color = color,
                        radius = 4.dp.toPx(),
                        center = Offset(pointX, centerY)
                    )
                }
            }
        }
    }
}

// X 좌표값을 기준으로 1.0 ~ 5.0 사이의 0.5 단위 값으로 변환해주는 헬퍼 함수
private fun calculateValueFromX(
    x: Float,
    totalWidth: Int,
    minRating: Float,
    maxRating: Float,
    steps: Int
): Float {
    val fraction = (x / totalWidth).coerceIn(0f, 1f)
    val stepIndex = (fraction * steps).roundToInt() // 가장 가까운 스텝으로 스냅
    return minRating + (stepIndex * 0.5f)
}

// 캔버스에 안쪽으로 오목하고 예쁜 마름모를 그려주는 확장 함수
private fun DrawScope.drawDiamondPoint(x: Float, centerY: Float, sizePx: Float, color: Color, roundnessFactor: Float = 0.3f) {
    val half = sizePx / 2
    val offset = half * roundnessFactor
    val path = Path().apply {
        moveTo(x, centerY - half)
        // 상단 -> 우측
        quadraticTo(x + offset, centerY - offset, x + half, centerY)
        // 우측 -> 하단
        quadraticTo(x + offset, centerY + offset, x, centerY + half)
        // 하단 -> 좌측
        quadraticTo(x - offset, centerY + offset, x - half, centerY)
        // 좌측 -> 상단
        quadraticTo(x - offset, centerY - offset, x, centerY - half)
        close()
    }
    drawPath(path = path, color = color)
}

@Preview
@Composable
fun MooditSliderPreview() {
    MooditTheme {
        MooditSlider(
            value = 2.0f,
            onValueChange = {}
        )
    }
}