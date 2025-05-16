package com.example.taskyapplication.agenda.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditItemDateTime(
    modifier: Modifier = Modifier,
    onClickDate: () -> Unit,
    onClickTime: () -> Unit
){
    AgendaItemDateTimeRow(
        modifier = modifier,
        isEditing = true,
        onClickDate = onClickDate,
        onClickTime = onClickTime,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AgendaEditDateTimePreview() {
    EditItemDateTime(
        onClickDate = {},
        onClickTime = {}
    )
}
