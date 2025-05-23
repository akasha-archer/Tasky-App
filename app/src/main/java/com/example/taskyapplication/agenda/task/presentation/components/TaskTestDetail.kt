package com.example.taskyapplication.agenda.task.presentation.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.task.SharedTaskViewModel
import com.example.taskyapplication.agenda.task.presentation.TaskUiState
import com.example.taskyapplication.ui.theme.TaskyTypography

// root screens
@Composable
fun EditScreenWrapper(
    onClickCancel: () -> Unit,
    onDoneEdit: () -> Unit,
    viewModel: SharedTaskViewModel = hiltViewModel()
) {
    val taskState = viewModel.uiState.collectAsStateWithLifecycle()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Edit1(
            onClickCancel = onClickCancel,
            onDoneEdit = onDoneEdit,
            onAction = { action ->
                viewModel.onTaskAction(action)
            },
            state = taskState.value
        )
    } else {
        // Fallback for older API versions
        Text(
            text = "This feature requires Android 12 or newer",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun DetailTest(
    modifier: Modifier = Modifier,
    state: TaskUiState,
    onClickNext: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .height(600.dp)
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Text(
            modifier = Modifier
                .height(400.dp)
                .padding(top = 64.dp)
                .background(color = Color.Yellow)
                .fillMaxWidth(),
            text = "${state.title} and testing the text box",
            style = TaskyTypography.headlineMedium
                .copy(color = Color.Black),
        )
//                Spacer(modifier = Modifier.height(16.dp)
        Button(
            onClick = onClickNext,
            modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Next Screen")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Edit1(
    modifier: Modifier = Modifier,
    onClickCancel: () -> Unit,
    onDoneEdit: () -> Unit,
    onAction: (AgendaItemAction) -> Unit,
    state: TaskUiState
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(top = 84.dp)
            .fillMaxWidth()
            .height(600.dp)
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        TextField(
            value = state.title,
            onValueChange = { newTitle ->
                onAction(AgendaItemAction.SetTitle(newTitle))
                Toast.makeText(context, " $newTitle", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            textStyle = MaterialTheme.typography.headlineMedium.copy(color = Color.Black),
        )

        TextButton(
            onClick = { onDoneEdit() },
            content = { Text(text = "Save") }
        )

        TextButton(
            onClick = onClickCancel,
            content = { Text(text = "Cancel") }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DetailTestPreview() {
    DetailTest(
        state = TaskUiState(),
        onClickNext = {}
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
fun Edit1Preview() {
    Edit1(
        onDoneEdit = {},
        onClickCancel = {},
        onAction = {},
        state = TaskUiState()
    )
}