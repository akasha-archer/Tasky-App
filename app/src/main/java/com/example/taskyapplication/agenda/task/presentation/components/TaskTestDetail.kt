package com.example.taskyapplication.agenda.task.presentation.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.task.SharedTaskViewModel

@Composable
fun DetailTest(
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit,
    viewModel: SharedTaskViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .height(600.dp)
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Text(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            text = state.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
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
    viewModel: SharedTaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var input by remember { mutableStateOf("")}

    Column(
        modifier = modifier
            .padding(top = 84.dp)
            .fillMaxWidth()
            .height(600.dp)
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        TextField(
            value = input,
            onValueChange = {
                input = it
//                onAction(AgendaItemAction.SetTitle(state.title))
                Toast.makeText(context," ${input}", Toast.LENGTH_SHORT).show()
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
        onClickNext = {}
    )
}

@Preview(showBackground = true)
@Composable
fun Edit1Preview() {
    Edit1(
        onDoneEdit = {},
        onClickCancel = {},
        onAction = {}
    )
}