package com.example.taskyapplication.main.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskyapplication.ui.theme.TaskyApplicationTheme

@Composable
fun TaskyScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    mainContent: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = topBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = Color.Black,
        content = { innerPadding ->
            mainContent(innerPadding)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ScaffoldTemplatePreview() {
    TaskyApplicationTheme {
        TaskyScaffold()
    }
}