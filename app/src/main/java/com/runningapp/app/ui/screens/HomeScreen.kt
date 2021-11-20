package com.runningapp.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.runningapp.app.R
import com.runningapp.app.ui.components.ActivityCard
import com.runningapp.app.ui.components.MonthlyGoalComponent
import com.runningapp.app.ui.utils.SimpleListDataItem

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MonthlyGoalComponent()

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.small_map),
                contentDescription = "Activity map preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 40.dp, 40.dp))
            )

            /* TO DO: real map goes here */

//            Surface(modifier = Modifier
//                .height(330.dp)
//                .fillMaxWidth(),
//                color = MaterialTheme.colorScheme.surfaceVariant
//            ) {
//                Text("To do map")
//            }
            Button(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(100.dp),
                shape = CircleShape
            ) {
                Text(
                    text = "GO!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
