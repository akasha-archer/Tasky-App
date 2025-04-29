package com.example.taskyapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.taskyapplication.agenda.presentation.AgendaScreen

@Composable
fun userStateScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    isRegisteredUser: Boolean,
    isTokenValid: Boolean
): @Composable () -> Unit {
    val uiToDisplay: @Composable () -> Unit = when {
        isTokenValid -> {
            { AgendaScreen(modifier = modifier, navController = navController) }
        }

        !isRegisteredUser -> {
            { AccountCreationScreen(modifier = modifier, navController = navController) }
        }

        isRegisteredUser -> {
            { LoginScreen(modifier = modifier, navController = navController) }
        }

        else -> {
            { AccountCreationScreen(modifier = modifier, navController = navController) }
        }
    }
    return uiToDisplay
}
