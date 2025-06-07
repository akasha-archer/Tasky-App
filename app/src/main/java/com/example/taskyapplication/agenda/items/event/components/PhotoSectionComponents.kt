package com.example.taskyapplication.agenda.items.event.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.agenda.items.event.data.EventPhoto
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun PhotoRowEmptyState(
    modifier: Modifier = Modifier,
    onAddPhotoClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = taskyColors.surfaceBright)
            .padding(vertical = 30.dp, horizontal = 30.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            modifier = Modifier,
            onClick = onAddPhotoClick,
            content = {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = taskyColors.outline,
                    contentDescription = "Add new Photo",
                    modifier = Modifier
                        .scale(1f)
                )
                Text(
                    text = " ADD PHOTOS",
                    color = taskyColors.outline,
                )
            }
        )
    }
}


@Composable
fun PhotoRow(
    modifier: Modifier = Modifier,
    photos: List<EventPhoto> = emptyList()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = taskyColors.surfaceBright)
    ) {
        val numPhotoRows = 2
        val numPhotoColumns = 5
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            text = "Photos",
            color = taskyColors.primary,
            style = TaskyTypography.bodyMedium
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            maxItemsInEachRow = 5,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat (numPhotoRows * numPhotoColumns) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            photos.forEach { photo ->
                LazyRow {
                    item {
                        PhotoItem(photoUrl = ImageBitmap(24, 24))
                    }
                }
                    }
                }

        }
}

@Composable
fun AddPhotoButton(
    modifier: Modifier = Modifier,
    onAddPhotoClick: () -> Unit = {}
) {
    Icon(
        imageVector = Icons.Default.Add,
        tint = taskyColors.outline,
        contentDescription = "Add Photo",
        modifier = modifier
            .background(color = Color.Transparent)
            .border(
                width = 2.dp,
                color = taskyColors.outline,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 24.dp, horizontal = 24.dp)
            .clickable { onAddPhotoClick() },
    )
}

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    onPhotoClick: () -> Unit = {},
    photoUrl: ImageBitmap
) {
    Image(
        bitmap = photoUrl,
        contentDescription = "event photo",
        modifier = modifier
            .clickable { onPhotoClick() }
            .background(color = Color.Transparent)
            .padding(vertical = 24.dp, horizontal = 24.dp)
            .border(
                width = 2.dp,
                color = taskyColors.outline,
                shape = RoundedCornerShape(4.dp)
            )
    )
}

@Preview(showBackground = true)
@Composable
fun AddPhotoButtonPreview() {
    AddPhotoButton()
}

@Preview(showBackground = true)
@Composable
fun ImageItemPreview() {
    PhotoItem(photoUrl = ImageBitmap(1, 1))
}

@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    PhotoRowEmptyState()
}

@Preview(showBackground = true)
@Composable
fun PhotoRowPreview() {
    PhotoRow()
}