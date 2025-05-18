package com.example.taskyapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskyapplication.agenda.presentation.AgendaScreen
import com.example.taskyapplication.agenda.presentation.ReminderOptions
import com.example.taskyapplication.agenda.task.EditDateTimeScreen
import com.example.taskyapplication.agenda.task.EditDescriptionRoot
import com.example.taskyapplication.agenda.task.EditTaskTitleRoot
import com.example.taskyapplication.agenda.task.TaskDetailScreen
import com.example.taskyapplication.auth.login.LoginScreenRoot
import com.example.taskyapplication.auth.register.RegisterScreenRoot
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
            val title = entry.savedStateHandle.get<String>("taskTitle") ?: "enter a title"
            val description = entry.savedStateHandle.get<String>("taskDescription") ?: "enter a description"
            val date = entry.savedStateHandle.get<String>("taskDate") ?: LocalDate.now().format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            )
            val time = entry.savedStateHandle.get<String>("taskTime") ?: LocalTime.now().format(
                DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            )
            val reminder = entry.savedStateHandle.get<String>("taskReminder") ?: ReminderOptions.THIRTY_MINUTES_BEFORE.value

            TaskDetailScreen(
                taskDescription = description,
                taskTitle = title,
                taskDate = date,
                taskTime = time,
                taskReminderTime = reminder,
                onClickEdit = {
                    navController.navigate(NavigationRoutes.TaskEditDateTimeScreen) {
                        popUpTo(NavigationRoutes.TaskDetailScreen) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable<NavigationRoutes.TaskEditDateTimeScreen> {
            EditDateTimeScreen(
                onSelectTitleEdit = {
                    navController.navigate(NavigationRoutes.TaskEditTitleScreen) 
                },
                onSelectDescriptionEdit = {
                    navController.navigate(NavigationRoutes.TaskEditDescriptionScreen)
                },
                onSave = { date, time, reminder ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("taskDate", date)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("taskTime", time)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("taskReminder", reminder)

                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavigationRoutes.TaskEditTitleScreen> {
            EditTaskTitleRoot(
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
