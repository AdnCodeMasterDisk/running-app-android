package com.runningapp.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.runningapp.app.ui.theme.ShimmerColorShades

@Composable
fun ShimmerAnimation(itemType: String) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )


    if (itemType == "activity") {
        ActivityShimmerItem(brush = brush)
    }
    if (itemType == "monthlyGoal"){
        MonthlyGoalShimmerItem(brush = brush)
    }


}


@Composable
fun ActivityShimmerItem(
    brush: Brush
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(165.dp)
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(brush = brush)
        )
    }
}

@Composable
fun MonthlyGoalShimmerItem(
    brush: Brush
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(115.dp)
                .padding(vertical = 4.dp, horizontal = 4.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(brush = brush)
        )
    }
}
