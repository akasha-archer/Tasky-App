package com.example.taskyapplication.agenda.items.main.presentation

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.domain.asLocalDateValue
import com.example.taskyapplication.agenda.items.main.AgendaMainViewModel
import com.example.taskyapplication.agenda.items.main.AgendaMainViewState
import com.example.taskyapplication.agenda.items.main.MainScreenAction
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.task.SharedTaskViewModel
import com.example.taskyapplication.agenda.presentation.components.TaskyDatePicker
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
) {
    val viewState by viewModel.agendaViewState.collectAsStateWithLifecycle()

    AgendaMainScreen(
        modifier = modifier,
        createNewItem = { type ->
            when (type) {
                AgendaItemType.EVENT -> launchNewEventScreen(null)
                AgendaItemType.REMINDER -> launchNewReminderScreen(null)
                AgendaItemType.TASK -> launchNewTaskScreen(null)
            }
        },
        onItemAction = { itemAction ->
            when(itemAction) {
                is AgendaItemAction.OpenExistingItem -> {
                    openSelectedItem(itemAction.id, itemAction.type)
                }
                else -> Unit
            }
            taskViewModel.executeActions(itemAction)
        },
        onAction = { action ->
            when (action) {
                is MainScreenAction.ItemToOpen -> {
//                    when (action.type) {
//                        AgendaItemType.EVENT -> launchNewEventScreen(action.itemId)
//                        AgendaItemType.REMINDER -> launchNewReminderScreen(action.itemId)
//                        AgendaItemType.TASK -> launchNewTaskScreen(action.itemId)
//
//                    }
//                    openSelectedItem(action.itemId, action.type)
                }

                is MainScreenAction.ItemToEdit -> {
//                    when (action.type) {
//                        AgendaItemType.EVENT -> launchNewEventScreen(action.itemId)
//                        AgendaItemType.REMINDER -> launchNewReminderScreen(action.itemId)
//                        AgendaItemType.TASK -> launchNewTaskScreen(action.itemId)
//                    }
                    editSelectedItem(action.itemId, action.type)
                }
                else -> { Unit }
            }
          viewModel.executeAgendaActions(action)
        },
        agendaViewState = viewState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaMainScreen(
    modifier: Modifier = Modifier,
    createNewItem: (AgendaItemType) -> Unit = {},
    onAction: (MainScreenAction) -> Unit = {},
    onItemAction: (AgendaItemAction) -> Unit = {},
    agendaViewState: AgendaMainViewState,
) {
    var showMonthDatePicker by rememberSaveable { mutableStateOf(false) }
    var showIconDatePicker by rememberSaveable { mutableStateOf(false) }
    var showLogoutDropDown by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var expandedItemId by rememberSaveable { mutableStateOf<String?>(null) }

    val ctxt = LocalContext.current
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
                userInitials = "JD"
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
                            Toast.makeText(
                                ctxt,
                                "open item clicked id $itemId type is $type",
                                Toast.LENGTH_SHORT).show()

                            // use action from agendatype?
                            onItemAction(AgendaItemAction.OpenExistingItem(itemId, type))
                            onAction(MainScreenAction.ItemToOpen(itemId, type))
                        },
                        onEditClick = { itemId, type ->
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
                            Toast.makeText(ctxt, "menu clicked id $itemId", Toast.LENGTH_SHORT).show()
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
                                datePickerState.selectedDateMillis?.asLocalDateValue() ?: LocalDate.now()
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
