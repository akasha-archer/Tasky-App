package com.example.taskyapplication.agenda.items.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.domain.toInitials
import com.example.taskyapplication.agenda.domain.toLocalDateAndTime
import com.example.taskyapplication.agenda.items.event.EventItemAction
import com.example.taskyapplication.agenda.items.event.SharedEventViewModel
import com.example.taskyapplication.agenda.items.main.AgendaMainViewModel
import com.example.taskyapplication.agenda.items.main.AgendaMainViewState
import com.example.taskyapplication.agenda.items.main.MainScreenAction
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.reminder.SharedReminderViewModel
import com.example.taskyapplication.agenda.items.task.SharedTaskViewModel
import com.example.taskyapplication.agenda.presentation.components.TaskyDatePicker
import com.example.taskyapplication.main.presentation.components.TaskyScaffold
import java.time.LocalDate

@Composable
fun AgendaMainRoot(
    modifier: Modifier = Modifier,
    launchNewEventScreen: (String?) -> Unit = {},
    launchNewReminderScreen: (String?) -> Unit = {},
    launchNewTaskScreen: (String?) -> Unit = {},
    openSelectedItem: (String, AgendaItemType) -> Unit,
    editSelectedItem: (String, AgendaItemType) -> Unit,
    viewModel: AgendaMainViewModel = hiltViewModel(),
    taskViewModel: SharedTaskViewModel = hiltViewModel(),
    reminderViewModel: SharedReminderViewModel = hiltViewModel(),
    eventViewModel: SharedEventViewModel = hiltViewModel()
) {
    val viewState by viewModel.agendaViewState.collectAsStateWithLifecycle()

    TaskyScaffold(
        modifier = modifier,
        mainContent = { innerPadding ->
            AgendaMainScreen(
                modifier = Modifier
                    .padding(innerPadding),
                createNewItem = { type ->
                    when (type) {
                        AgendaItemType.EVENT -> launchNewEventScreen(null)
                        AgendaItemType.REMINDER -> launchNewReminderScreen(null)
                        AgendaItemType.TASK -> launchNewTaskScreen(null)
                    }
                },
                onEventAction = { eventAction ->
                    when (eventAction) {
                        is EventItemAction.OpenExistingEvent -> {
                            openSelectedItem(eventAction.eventId, AgendaItemType.EVENT)
                        }

                        is EventItemAction.EditExistingEvent -> {
                            editSelectedItem(eventAction.eventId, AgendaItemType.EVENT)
                        }

                        else -> Unit
                    }
                    eventViewModel.executeActions(eventAction)
                },
                onItemAction = { itemAction ->
                    when (itemAction) {
                        is AgendaItemAction.OpenExistingReminder -> {
                            openSelectedItem(itemAction.id, AgendaItemType.REMINDER)
                        }

                        is AgendaItemAction.OpenExistingTask -> {
                            openSelectedItem(itemAction.id, AgendaItemType.TASK)
                        }

                        is AgendaItemAction.EditExistingReminder -> {
                            editSelectedItem(itemAction.id, AgendaItemType.REMINDER)
                        }

                        is AgendaItemAction.EditExistingTask -> {
                            editSelectedItem(itemAction.id, AgendaItemType.TASK)
                        }

                        else -> Unit
                    }
                    taskViewModel.executeActions(itemAction)
                    reminderViewModel.executeActions(itemAction)
                },
                onAction = { action ->
                    when (action) {
                        is MainScreenAction.ItemToOpen -> {
                            openSelectedItem(action.itemId, action.type)
                        }

                        is MainScreenAction.ItemToEdit -> {
                            editSelectedItem(action.itemId, action.type)
                        }

                        else -> {
                            Unit
                        }
                    }
                    viewModel.executeAgendaActions(action)
                },
                agendaViewState = viewState
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaMainScreen(
    modifier: Modifier = Modifier,
    createNewItem: (AgendaItemType) -> Unit = {},
    onAction: (MainScreenAction) -> Unit = {},
    onItemAction: (AgendaItemAction) -> Unit = {},
    onEventAction: (EventItemAction) -> Unit = {},
    agendaViewState: AgendaMainViewState,
) {
    var showMonthDatePicker by rememberSaveable { mutableStateOf(false) }
    var showIconDatePicker by rememberSaveable { mutableStateOf(false) }
    var showLogoutDropDown by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var expandedItemId by rememberSaveable { mutableStateOf<String?>(null) }
//    val userInitials = agendaViewState.userFullName?.toInitials()?.ifEmpty { "zz" }

    TaskyBaseScreen(
        modifier = modifier,
        screenHeader = {
            MainScreenHeader(
                launchDatePicker = {
                    showMonthDatePicker = true
                    showIconDatePicker = true
                },
                launchLogoutDropDown = { showLogoutDropDown = true },
                onLogoutClick = { onAction(MainScreenAction.LogoutUser) },
                onDismissRequest = { showLogoutDropDown = false },
                showLogoutDropDown = showLogoutDropDown,
                userInitials =  agendaViewState.userFullName?.toInitials()?.ifEmpty { "zz" } ?: "gg"
            )
        },
        mainContent = {
            var showFabPopup by rememberSaveable { mutableStateOf(false) }
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                ) {
                    AgendaScreenScrollableDates()
                    AgendaSummary(
                        modifier = Modifier,
                        dailySummary = agendaViewState.combinedSummaryList,
                        dateHeading = agendaViewState.displayDateHeading,
                        onOpenClick = { itemId, type ->
                            when (type) {
                                AgendaItemType.EVENT -> {
                                    onEventAction(EventItemAction.OpenExistingEvent(itemId))
                                }

                                AgendaItemType.REMINDER -> {
                                    onItemAction(AgendaItemAction.OpenExistingReminder(itemId))
                                }

                                AgendaItemType.TASK -> {
                                    onItemAction(AgendaItemAction.OpenExistingTask(itemId))
                                }
                            }
                            onAction(MainScreenAction.ItemToOpen(itemId, type))
                        },
                        onEditClick = { itemId, type ->
                            when (type) {
                                AgendaItemType.EVENT -> {
                                    onEventAction(EventItemAction.EditExistingEvent(itemId))
                                }

                                AgendaItemType.REMINDER -> {
                                    onItemAction(AgendaItemAction.EditExistingReminder(itemId))
                                }

                                AgendaItemType.TASK -> {
                                    onItemAction(AgendaItemAction.OpenExistingTask(itemId))
                                }
                            }
                            onAction(MainScreenAction.ItemToEdit(itemId, type))
                        },
                        onDeleteClick = { itemId, type ->
                            onAction(MainScreenAction.ItemToDelete(itemId, type))
                        },
                        onToggleItemMenu = { itemId ->
                            expandedItemId = if (expandedItemId == itemId) {
                                null
                            } else {
                                itemId
                            }
                        },
                        currentlyExpandedItemId = expandedItemId

                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                ) {
                    MainScreenFab(
                        onClickFab = { showFabPopup = true },
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 24.dp)
                    )
                    FabPopupMenu(
                        modifier = Modifier,
                        onDismissRequest = { showFabPopup = false },
                        navigateToScreen = {
                            createNewItem(it)
                            showFabPopup = false
                        },
                        isExpanded = showFabPopup
                    )
                } // end of Fab popup Box
            } // end of outer Box
            if (showMonthDatePicker || showIconDatePicker) {
                TaskyDatePicker(
                    modifier = Modifier,
                    datePickerState = datePickerState,
                    onDismiss = {
                        showMonthDatePicker = false
                        showIconDatePicker = false
                    },
                    onConfirm = {
                        onAction(
                            MainScreenAction.SelectAgendaDate(
                                datePickerState.selectedDateMillis?.toLocalDateAndTime()?.first
                                    ?: LocalDate.now()
                            )
                        )
                        showMonthDatePicker = false
                        showIconDatePicker = false
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun AgendaMainScreenPreview() {
    AgendaMainScreen(
        agendaViewState = AgendaMainViewState()
    )
}
