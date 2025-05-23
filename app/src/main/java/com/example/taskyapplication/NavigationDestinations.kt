package com.example.taskyapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskyapplication.agenda.presentation.AgendaScreen
import com.example.taskyapplication.agenda.task.presentation.components.DetailTest
import com.example.taskyapplication.auth.login.LoginScreenRoot
import com.example.taskyapplication.auth.register.RegisterScreenRoot
import kotlinx.serialization.Serializable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.navigation
import com.example.taskyapplication.agenda.task.SharedTaskViewModel
import com.example.taskyapplication.agenda.task.presentation.components.EditScreenWrapper

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
        startDestination = NavigationRoutes.TaskEditGraph
            //NavigationRoutes.TaskScreen
//            when {
//            isLoggedIn -> NavigationRoutes.AgendaScreen
//            isUserRegistered -> NavigationRoutes.LoginScreen
//            else -> NavigationRoutes.RegisterScreen
//        }
    ) {
        // Task screens subgraph
        navigation<NavigationRoutes.TaskEditGraph>(
            startDestination = NavigationRoutes.TaskScreen
        ){
            composable< NavigationRoutes.TaskScreen> { entry ->
                val viewmodel = entry.sharedViewModel<SharedTaskViewModel>(navController)
                val state by viewmodel.uiState.collectAsStateWithLifecycle()

                DetailTest(
                    state = state,
                    onClickNext = {
                        navController.navigate(NavigationRoutes.EditScreen)
                    },
                )
            }
            composable<NavigationRoutes.EditScreen> { entry ->
                val viewmodel = entry.sharedViewModel<SharedTaskViewModel>(navController)
//                val state by viewmodel.uiState.collectAsStateWithLifecycle()

                EditScreenWrapper(
                    onClickCancel = {
                        navController.navigate(NavigationRoutes.TaskScreen) {
                            popUpTo(NavigationRoutes.TaskScreen) {
                                inclusive = false
                                saveState = true
                            }
                        }
                    },
                    onDoneEdit = { navController.popBackStack() },
                    viewModel = viewmodel
                )
            }
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

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

sealed interface NavigationRoutes {

    @Serializable
    data object LoginScreen : NavigationRoutes

    @Serializable
    data object RegisterScreen : NavigationRoutes

    @Serializable // route for nested graph for Task screens
    data object TaskEditGraph: NavigationRoutes

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