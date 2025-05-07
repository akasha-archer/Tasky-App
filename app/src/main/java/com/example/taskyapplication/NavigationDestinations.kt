package com.example.taskyapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskyapplication.agenda.presentation.AgendaScreen
import com.example.taskyapplication.auth.presentation.RegisterRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isLoggedIn: Boolean = false,
    isUserRegistered: Boolean = false
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = UserStateScreen
    ) {
        composable<UserStateScreen> {
            LaunchedEffect(isLoggedIn, isUserRegistered) {
                val route = when {
                    isLoggedIn -> AgendaScreen
                    isUserRegistered -> LoginScreen
                    else -> RegisterScreen
                }
                navController.navigate(route) {
                    popUpTo(UserStateScreen) { inclusive = true }
                }
            }
        }
        composable<AgendaScreen> {
            AgendaScreen()
        }

        composable<LoginScreen> {
//            LogInScreen()
        }

        composable<RegisterScreen> {
            RegisterRoot()
        }
    }
}


// navigation screen destinations
@Serializable
object UserStateScreen

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
object AgendaScreen

@Serializable
object EventScreen

@Serializable
object TaskScreen

@Serializable
object ReminderScreen






