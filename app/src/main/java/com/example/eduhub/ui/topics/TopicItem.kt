package com.example.eduhub.ui.topics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.eduhub.R

@Composable
fun TopicItem(
    topic: TopicItemState,
    viewModel: TopicViewModel = viewModel(),
) {
    Card(
        onClick = {},
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 8.dp)
        ) {
            if (!topic.icon.isNullOrEmpty()) {
                AsyncImage(
                    model = topic.icon,
                    contentDescription = "Icon image",
                    modifier = Modifier
                        .padding(8.dp)
                        .height(24.dp)
                )
            } else {
                Icon(
                    painterResource(R.drawable.settings_3_line),
                    contentDescription = "Icon image",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
            }
            Text(
                text = topic.name,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
fun TopicItemPreview() {
    TopicItem(
        topic = TopicItemState(
            id = "1",
            name = "Topic 1",
            icon = ""
        )
    )
}