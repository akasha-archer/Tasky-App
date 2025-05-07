package com.example.taskyapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskyapplication.agenda.presentation.AgendaScreen
import com.example.taskyapplication.auth.register.RegisterRoot
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
        startDestination = NavigationRoutes.UserStateScreen
    ) {
        composable<NavigationRoutes.UserStateScreen> {
            LaunchedEffect(isLoggedIn, isUserRegistered) {
                val route = when {
                    isLoggedIn -> NavigationRoutes.AgendaScreen
                    isUserRegistered -> NavigationRoutes.LoginScreen
                    else -> NavigationRoutes.RegisterScreen
                }
                navController.navigate(route) {
                    popUpTo(NavigationRoutes.UserStateScreen) { inclusive = true }
                }
            }
        }
        composable<NavigationRoutes.AgendaScreen> {
            AgendaScreen()
        }

        composable<NavigationRoutes.LoginScreen> {
//            LogInScreen()
        }

        composable<NavigationRoutes.RegisterScreen> {
            RegisterRoot(
                onLoginClick = {
                    navController.navigate(NavigationRoutes.LoginScreen) {
                        popUpTo(NavigationRoutes.RegisterScreen) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(NavigationRoutes.LoginScreen)
                }
            )
        }
    }
}

sealed interface NavigationRoutes {
    @Serializable
    data object UserStateScreen : NavigationRoutes

    @Serializable
    data object LoginScreen : NavigationRoutes

    @Serializable
    data object RegisterScreen : NavigationRoutes

    @Serializable
    data object AgendaScreen : NavigationRoutes

    @Serializable
    data object EventScreen : NavigationRoutes

    @Serializable
    data object TaskScreen : NavigationRoutes

    @Serializable
    data object ReminderScreen : NavigationRoutes
}
