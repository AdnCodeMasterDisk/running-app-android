package com.runningapp.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.runningapp.app.ui.utils.SimpleListDataItem

@Composable
fun ChallengeTile(simpleListDataItem: SimpleListDataItem) {

    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
            .size(110.dp, 150.dp)
            .clip(RoundedCornerShape(20.dp)),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = simpleListDataItem.text,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "10 234",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "participants",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
fun ChallengeTilePreview() {
    ChallengeTile(SimpleListDataItem("30 km in October"))
}