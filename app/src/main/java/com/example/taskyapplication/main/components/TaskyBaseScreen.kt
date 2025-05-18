package com.example.taskyapplication.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/***
 * This is the base screen for all the screens in the app.
 * It will contain the header and the main content of the screen.
 *
 * @param screenHeader: The header of the screen that displays the title and the action buttons.
 * @param mainContent: The main content of the screen.
 */

@Composable
fun TaskyBaseScreen(
    modifier: Modifier = Modifier,
    screenHeader: @Composable () -> Unit,
    mainContent: @Composable () -> Unit,
    isAgendaEditScreen: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Black)
    ) {
        screenHeader()
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
                .background(color =
                if(isAgendaEditScreen)
                    Color.White
                else
                    Color.Black)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            mainContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskyBaseScreenPreview() {
    TaskyBaseScreen(
        screenHeader = {
            Text(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = Color.Black),
                textAlign = TextAlign.Center,
                text = "Create  Account",
                color = Color.White,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
        },
        mainContent = {

        }
    )
}