package com.example.taskyapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskyScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    titleBarContent: @Composable () -> Unit = {},
    mainContent: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = topBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentWindowInsets = contentWindowInsets,
        content = { innerPadding ->
            Column() {
                titleBarContent()
            }
            Card(
                shape = RoundedCornerShape(
                    topEnd = 40.dp,
                    topStart = 40.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                ),
                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                mainContent(innerPadding)
            }
        }
    )
}