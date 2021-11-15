package com.runningapp.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(
    progress: Float,
    number: Int,
    radius: Dp = 30.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 12.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            progress,
            animationSpec = tween(durationMillis = animDuration, delayMillis = animDelay)
        )
    }

    Box(
       contentAlignment = Alignment.Center,
       modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                -90f,
                360 * animatedProgress.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (animatedProgress.value * number).toInt().toString() + "%"
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CircularProgressBarPreview() {
    CircularProgressBar(0.6f, 100)
}
