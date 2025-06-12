package com.example.taskyapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskyapplication.auth.login.LoginScreenRoot
import com.example.taskyapplication.auth.register.RegisterScreenRoot
import kotlinx.serialization.Serializable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.navigation
import com.example.taskyapplication.agenda.items.event.SharedEventViewModel
import com.example.taskyapplication.agenda.items.event.screens.EventDescriptionRoot
import com.example.taskyapplication.agenda.items.event.screens.EventDetailRoot
import com.example.taskyapplication.agenda.items.event.screens.EventEditDateTimeRoot
import com.example.taskyapplication.agenda.items.event.screens.EventTitleRoot
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.main.presentation.AgendaMainRoot
import com.example.taskyapplication.agenda.items.reminder.SharedReminderViewModel
import com.example.taskyapplication.agenda.items.reminder.presentation.screens.ReminderDetailRoot
import com.example.taskyapplication.agenda.items.reminder.presentation.screens.ReminderEditDateTimeRoot
import com.example.taskyapplication.agenda.items.reminder.presentation.screens.ReminderEditDescriptionRoot
import com.example.taskyapplication.agenda.items.reminder.presentation.screens.ReminderEditTitleRoot
import com.example.taskyapplication.agenda.items.task.SharedTaskViewModel
import com.example.taskyapplication.agenda.items.task.presentation.screens.TaskDetailRoot
import com.example.taskyapplication.agenda.items.task.presentation.screens.TaskEditDateTimeRoot
import com.example.taskyapplication.agenda.items.task.presentation.screens.TaskEditDescriptionRoot
import com.example.taskyapplication.agenda.items.task.presentation.screens.TaskEditTitleRoot

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
        startDestination = NavigationRoutes.AgendaScreen
        //NavigationRoutes.TaskScreen
//            when {
//            isLoggedIn -> NavigationRoutes.AgendaScreen
//            isUserRegistered -> NavigationRoutes.LoginScreen
//            else -> NavigationRoutes.RegisterScreen
//        }
    ) {
        // Task screens subgraph
        navigation<NavigationRoutes.TaskEditGraph>(
            startDestination = NavigationRoutes.TaskDetailScreen(null)
        ) {
            composable<NavigationRoutes.TaskDetailScreen> { entry ->
                val viewmodel = entry.sharedViewModel<SharedTaskViewModel>(navController)
                TaskDetailRoot(
                    onClickEdit = {
                        navController.navigate(
                            NavigationRoutes.TaskEditDateTime(null)
                        )
                    },
                    onClickClose = {
                        navController.navigate(NavigationRoutes.AgendaScreen)
                    },
                    taskViewModel = viewmodel
                )
            }
            composable<NavigationRoutes.TaskEditDateTime> { entry ->
                val viewmodel = entry.sharedViewModel<SharedTaskViewModel>(navController)
                TaskEditDateTimeRoot(
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onSelectEditTitle = {
                        navController.navigate(NavigationRoutes.TaskEditTitle)
                    },
                    onSelectEditDescription = {
                        navController.navigate(NavigationRoutes.TaskEditDescription)
                    },
                    taskViewModel = viewmodel,
                )
            }
            composable<NavigationRoutes.TaskEditTitle> { entry ->
                val viewmodel = entry.sharedViewModel<SharedTaskViewModel>(navController)
                TaskEditTitleRoot(
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    taskViewModel = viewmodel
                )
            }
            composable<NavigationRoutes.TaskEditDescription> { entry ->
                val viewmodel = entry.sharedViewModel<SharedTaskViewModel>(navController)
                TaskEditDescriptionRoot(
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    taskViewModel = viewmodel
                )
            }
        } // end task subgraph

        // Reminder screens subgraph
        navigation<NavigationRoutes.ReminderEditGraph>(
            startDestination = NavigationRoutes.ReminderDetail(null)
        ) {
            composable<NavigationRoutes.ReminderDetail> { entry ->
                val viewmodel = entry.sharedViewModel<SharedReminderViewModel>(navController)
                ReminderDetailRoot(
                    onClickEdit = {
                        navController.navigate(NavigationRoutes.ReminderDateTime(null))
                    },
                    onClickClose = {
                        navController.navigate(NavigationRoutes.AgendaScreen)
                    },
                    reminderViewModel = viewmodel
                )
            }
            composable<NavigationRoutes.ReminderDateTime> { entry ->
                val viewmodel = entry.sharedViewModel<SharedReminderViewModel>(navController)
                ReminderEditDateTimeRoot(
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onSelectEditTitle = {
                        navController.navigate(NavigationRoutes.ReminderEditTitle)
                    },
                    onSelectEditDescription = {
                        navController.navigate(NavigationRoutes.ReminderEditDescription)
                    },
                    reminderViewModel = viewmodel
                )
            }
            composable<NavigationRoutes.ReminderEditTitle> { entry ->
                val viewmodel = entry.sharedViewModel<SharedReminderViewModel>(navController)
                ReminderEditTitleRoot(
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    reminderViewModel = viewmodel
                )
            }
            composable<NavigationRoutes.ReminderEditDescription> { entry ->
                val viewmodel = entry.sharedViewModel<SharedReminderViewModel>(navController)
                ReminderEditDescriptionRoot(
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    reminderViewModel = viewmodel
                )
            }
        } // end reminder subgraph

        // Event screens subgraph
        navigation<NavigationRoutes.EventEditGraph>(
            startDestination = NavigationRoutes.EventDetail(null)
        ) {
            composable<NavigationRoutes.EventDetail> { entry ->
                val viewmodel = entry.sharedViewModel<SharedEventViewModel>(navController)
                EventDetailRoot(
                    onClickEdit = {
                        navController.navigate(NavigationRoutes.EventDateTime(null))
                    },
                    onClickClose = {
                        navController.navigate(NavigationRoutes.AgendaScreen)
                    },
                    eventViewModel = viewmodel
                )
            }
            composable<NavigationRoutes.EventDateTime> { entry ->
                val viewmodel = entry.sharedViewModel<SharedEventViewModel>(navController)
                EventEditDateTimeRoot(
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onSelectEditTitle = {
                        navController.navigate(NavigationRoutes.EventEditTitle)
                    },
                    onSelectEditDescription = {
                        navController.navigate(NavigationRoutes.EventEditDescription)
                    },
                    eventViewModel = viewmodel
                )
            }
            composable<NavigationRoutes.EventEditTitle> { entry ->
                val viewmodel = entry.sharedViewModel<SharedEventViewModel>(navController)
                EventTitleRoot(
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    eventViewModel = viewmodel
                )
            }
            composable<NavigationRoutes.EventEditDescription> { entry ->
                val viewmodel = entry.sharedViewModel<SharedEventViewModel>(navController)
                EventDescriptionRoot(
                    onClickSave = {
                        navController.navigateUp()
                    },
                    onClickCancel = {
                        navController.navigateUp()
                    },
                    eventViewModel = viewmodel
                )
            }
        } // end event subgraph

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
            AgendaMainRoot(
                launchNewEventScreen = { itemId ->
                    navController.navigate(NavigationRoutes.EventDateTime(null))
                },
                launchNewReminderScreen = { itemId ->
                    navController.navigate(NavigationRoutes.ReminderDateTime(null))
                },
                launchNewTaskScreen = { itemId ->
                    navController.navigate(NavigationRoutes.TaskEditDateTime(null))

                },
                openSelectedItem = { itemId, itemType ->
                    when (itemType) {
                        AgendaItemType.EVENT -> navController.navigate(
                            NavigationRoutes.EventDetail(taskId = itemId)
                        )
                        AgendaItemType.TASK -> navController.navigate(
                            NavigationRoutes.TaskDetailScreen(taskId = itemId)
                        )
                        AgendaItemType.REMINDER -> navController.navigate(
                            NavigationRoutes.ReminderDetail(taskId = itemId)
                        )
                    }
                },
                editSelectedItem = { itemId, itemType ->
                    when (itemType) {
                        AgendaItemType.EVENT -> navController.navigate(
                            NavigationRoutes.EventDateTime(taskId = itemId)
                        )
                        AgendaItemType.TASK -> navController.navigate(
                            NavigationRoutes.TaskEditDateTime(taskId = itemId)
                        )
                        AgendaItemType.REMINDER -> navController.navigate(
                            NavigationRoutes.ReminderDateTime(taskId = itemId)
                        )
                    }
                },
            )
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

    @Serializable // route for nested graph for Auth screens that lead to Agenda
    data object AuthToAgendaGraph : NavigationRoutes

    @Serializable // route for nested graph for Task screens
    data object TaskEditGraph : NavigationRoutes

    @Serializable // route for nested graph for Task screens
    data object ReminderEditGraph : NavigationRoutes

    @Serializable // route for nested graph for Event screens
    data object EventEditGraph : NavigationRoutes

    @Serializable
    data object AgendaScreen : NavigationRoutes

    @Serializable
    data object EventScreen : NavigationRoutes

    @Serializable
    data class TaskDetailScreen(val taskId: String?) : NavigationRoutes

    @Serializable
    data class TaskEditDateTime(val taskId: String?) : NavigationRoutes

    @Serializable
    data object TaskEditTitle : NavigationRoutes

    @Serializable
    data object TaskEditDescription : NavigationRoutes

    @Serializable
    data class ReminderDetail(val taskId: String?) : NavigationRoutes

    @Serializable
    data class ReminderDateTime(val taskId: String?) : NavigationRoutes

    @Serializable
    data object ReminderEditDescription : NavigationRoutes

    @Serializable
    data object ReminderEditTitle : NavigationRoutes

    @Serializable
    data class EventDetail(val taskId: String?) : NavigationRoutes

    @Serializable
    data class EventDateTime(val taskId: String?) : NavigationRoutes

    @Serializable
    data object EventEditDescription : NavigationRoutes

    @Serializable
    data object EventEditTitle : NavigationRoutes
}