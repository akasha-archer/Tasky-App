package com.example.taskyapplication.agenda.items.event.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.items.event.domain.EventImageItem
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun PhotoRowEmptyState(
    modifier: Modifier = Modifier,
    launchPhotoPicker: () -> Unit = {},
) {
    val screenWidth = LocalWindowInfo.current.containerSize.width.dp
    Row(
        modifier = modifier
            .fillMaxWidth()
            .requiredWidth(screenWidth + 32.dp)
            .background(color = taskyColors.surfaceHigh)
            .padding(vertical = 30.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            modifier = Modifier,
            onClick = { launchPhotoPicker() },
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
    detailPhotos: List<String> = emptyList(),
    photosToShow: List<EventImageItem> = emptyList(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = taskyColors.surface)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
            text = stringResource(id = R.string.event_photos_section),
            color = taskyColors.primary,
            style = TaskyTypography.bodyMedium
        )
        LazyVerticalGrid(
            modifier = Modifier
                .padding(end = 16.dp)
                .fillMaxWidth(),
            columns = GridCells.Fixed(5),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(photosToShow) { photo ->
                AsyncImage(
                    model = photo,
                    contentDescription = "event photo",
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .border(
                            width = 2.dp,
                            color = taskyColors.outline,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
            if (photosToShow.size < 10)
                item {
                    AddPhotoButton()
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