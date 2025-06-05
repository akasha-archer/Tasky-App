package com.example.taskyapplication.agenda.items.event.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PhotoRow(
    modifier: Modifier = Modifier,

) {

}

@Composable
fun AddPhotoButton(
    modifier: Modifier = Modifier,
    onAddPhotoClick: () -> Unit = {}
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Add Photo",
        modifier = modifier
            .background(color = Color.Transparent)
            .padding(vertical = 8.dp, horizontal = 8.dp)
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
    )
}

@Preview(showBackground = true)
@Composable
fun AddPhotoButtonPreview() {
    AddPhotoButton()
}