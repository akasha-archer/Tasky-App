package com.example.taskyapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskyapplication.agenda.presentation.AgendaScreen
import com.example.taskyapplication.agenda.task.presentation.components.DetailTest
import com.example.taskyapplication.agenda.task.presentation.components.Edit1
import com.example.taskyapplication.auth.login.LoginScreenRoot
import com.example.taskyapplication.auth.register.RegisterScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isLoggedIn: Boolean,
    isUserRegistered: Boolean
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationRoutes.TaskScreen
//            when {
//            isLoggedIn -> NavigationRoutes.AgendaScreen
//            isUserRegistered -> NavigationRoutes.LoginScreen
//            else -> NavigationRoutes.RegisterScreen
//        }
    ) {

        composable<NavigationRoutes.TaskScreen> {
            DetailTest(
                onClickNext = {
                    navController.navigate(NavigationRoutes.EditScreen)
                },
            )
        }

        composable<NavigationRoutes.EditScreen> {
            Edit1(
                onClickCancel = {
                    navController.popBackStack()
                },
                onDoneEdit = {
                    navController.popBackStack()
                },
                onAction = {}
            )
        }

        composable<NavigationRoutes.RegisterScreen> {
            RegisterScreenRoot(
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
        composable<NavigationRoutes.LoginScreen> {
            LoginScreenRoot(
                onSignUpClick = {
                    navController.navigate(NavigationRoutes.RegisterScreen) {
                        popUpTo(NavigationRoutes.LoginScreen) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onLoginSuccess = {
                    navController.navigate(NavigationRoutes.AgendaScreen) {
                        popUpTo(NavigationRoutes.LoginScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<NavigationRoutes.AgendaScreen> {
            AgendaScreen()
        }

    }
}

sealed interface NavigationRoutes {

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
    data object EditScreen : NavigationRoutes

    @Serializable
    data object ReminderScreen : NavigationRoutes
}
