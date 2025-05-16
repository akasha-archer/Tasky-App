package com.example.taskyapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskyapplication.agenda.presentation.AgendaScreen
import com.example.taskyapplication.agenda.task.EditDescriptionRoot
import com.example.taskyapplication.agenda.task.EditTaskTitleRoot
import com.example.taskyapplication.agenda.task.TaskDetailScreen
import com.example.taskyapplication.auth.login.LoginScreenRoot
import com.example.taskyapplication.auth.register.RegisterScreenRoot
import kotlinx.serialization.Serializable

@RequiresApi(Build.VERSION_CODES.O)
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
        startDestination = NavigationRoutes.TaskDetailScreen
//        when {
//            isLoggedIn -> NavigationRoutes.AgendaScreen
//            isUserRegistered -> NavigationRoutes.LoginScreen
//            else -> NavigationRoutes.RegisterScreen
//        }
    ) {
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

        composable<NavigationRoutes.TaskDetailScreen> { entry ->
            val title = entry.savedStateHandle.get<String>("taskTitle") ?: ""
            val description = entry.savedStateHandle.get<String>("taskDescription") ?: ""
            TaskDetailScreen(
                taskDescription = description,
                taskTitle = title,
                onClickEdit = {
                    navController.navigate(NavigationRoutes.TaskEditTitleScreen)
                    {
                        popUpTo(NavigationRoutes.TaskDetailScreen) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable<NavigationRoutes.TaskEditTitleScreen> {
            EditTaskTitleRoot(
                // pass boolean for isEditing here
                // so edit icons are removed when backstack is popped?
                onClickSave = { newTitle  ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("taskTitle", newTitle)
                    navController.popBackStack()
                },
                onClickCancel = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavigationRoutes.TaskEditDescriptionScreen> {
            EditDescriptionRoot(
                onClickSave = { newDescription ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("taskDescription", newDescription)
                    navController.popBackStack()
                },
                onClickCancel = {
                    navController.popBackStack()
                }
            )
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
    data object EventDetailScreen : NavigationRoutes

    @Serializable
    data object TaskDetailScreen : NavigationRoutes

    @Serializable
    data object TaskEditDateTimeScreen : NavigationRoutes

    @Serializable
    data object TaskEditTitleScreen : NavigationRoutes

    @Serializable
    data object TaskEditDescriptionScreen : NavigationRoutes

    @Serializable
    data object ReminderDetailScreen : NavigationRoutes
}
