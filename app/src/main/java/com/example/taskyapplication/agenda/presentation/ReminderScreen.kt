package com.example.taskyapplication.agenda.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.main.components.TaskyBaseScreen
import com.example.taskyapplication.main.components.TaskyScaffold
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderDetailRoot(
    modifier: Modifier = Modifier
) {
    TaskyScaffold(
        mainContent = {
            ReminderDetailScreen(modifier)
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderDetailScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
               DetailScreenHeader()
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
                                            imageVector = Icons.Default.Favorite,
                                            tint = taskyColors.primary,
                                            contentDescription = "Edit Icon"
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            modifier = Modifier.padding(start = 8.dp),
                                            text = "Reminder".uppercase(),
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
                                            imageVector = Icons.Filled.FavoriteBorder,
                                            tint = taskyColors.primary,
                                            contentDescription = "Edit Icon"
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            modifier = Modifier.padding(start = 12.dp),
                                            text = "Project Name",
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
                                            text = "Reminder Time",
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ReminderScreenPreview() {
    ReminderDetailRoot()
}