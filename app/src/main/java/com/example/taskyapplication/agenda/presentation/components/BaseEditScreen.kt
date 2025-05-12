package com.example.taskyapplication.agenda.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.presentation.AgendaItem
import com.example.taskyapplication.main.components.TaskyBaseScreen
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaseEditScreen(
    modifier: Modifier = Modifier,
    agendaItemType: String = "",
    agendaItemTitle: String = "",
    agendaItemDescription: String = ""
) {
    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
                DetailScreenHeader(
                    onClickEdit = {}
                )
            },
            mainContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxHeight()
                            .padding(
                                horizontal = 16.dp
                            )
                            .padding(top = 48.dp)
                    ) {
                        AgendaItem(
                            agendaItemType = {
                                AgendaIconTextRow(
                                    itemIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.agenda_type_square),
                                            tint = taskyColors.primary,
                                            contentDescription = ""
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            modifier = Modifier.padding(start = 8.dp),
                                            text = agendaItemType.uppercase(),
                                            style = TaskyTypography.labelMedium,
                                            color = taskyColors.onSurface
                                        )
                                    }
                                )
                            },
                            agendaItemTitle = {
                                AgendaIconTextRow(
                                    itemIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.agenda_title_icon),
                                            tint = taskyColors.primary,
                                            contentDescription = ""
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            modifier = Modifier.padding(start = 12.dp),
                                            text = agendaItemTitle,
                                            style = TaskyTypography.headlineLarge,
                                            color = taskyColors.onSurface
                                        )
                                    }
                                )
                            },
                            agendaItemDescription = {
                                AgendaDescriptionText()
                            },
                            agendaItemStartTime = {
                                AgendaItemTimeRow()
                            },
                            agendaItemReminderTime = {
                                AgendaIconTextRow(
                                    itemIcon = {
                                        Icon(
                                            modifier = Modifier
                                                .alpha(0.7f)
                                                .drawBehind {
                                                    drawRoundRect(
                                                        color = Color(0xFFF2F3F7),
                                                        cornerRadius = CornerRadius(3.dp.toPx())
                                                    )
                                                }.padding(horizontal = 4.dp, vertical = 4.dp),
                                            imageVector = Icons.Outlined.Notifications,
                                            tint = taskyColors.onSurfaceVariant,
                                            contentDescription = "Reminder Icon"
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            modifier = Modifier.padding(start = 8.dp),
                                            text = "30 minutes before",
                                            style = TaskyTypography.bodyMedium,
                                            color = taskyColors.onSurface
                                        )
                                    }
                                )
                            },
                        )
                        AgendaItemDeleteButton(
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .align(Alignment.BottomCenter),
                            onClick = {}
                        )
                    }
                }
            }
        )
    }
}
