package com.example.taskyapplication.agenda.items.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.presentation.components.AgendaScreenDivider
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAttendeeBottomSheet(
    modifier: Modifier = Modifier,
    emailInput: String,
    isLoading: Boolean = false,
    isButtonEnabled: Boolean = false,
    isValidEmail: Boolean = false,
    onEmailInputChange: (String) -> Unit, // Callback when email text changes
    onConfirmAdd: () -> Unit,
    onCancelAddAttendee: () -> Unit = {},
) {
    val screenWidth = LocalWindowInfo.current.containerSize.width.dp

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onCancelAddAttendee() },
        sheetState = rememberModalBottomSheetState(),
        containerColor = taskyColors.surface,
        content = {
            Column(
                modifier = Modifier
                    .background(color = taskyColors.surface)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = stringResource(R.string.add_visitor).uppercase(),
                        style = TaskyTypography.labelMedium.copy(
                            color = taskyColors.primary,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close bottom sheet",
                        tint = taskyColors.primary,
                        modifier = Modifier
                            .clickable{ onCancelAddAttendee() }
                    )
                } // end of row
                AgendaScreenDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredWidth(screenWidth + 32.dp)
                        .padding(bottom = 24.dp)
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = emailInput,
                    onValueChange = onEmailInputChange,
                    shape = RoundedCornerShape(12.dp),
                    supportingText = {
                        if (isValidEmail && emailInput.isNotEmpty()) {
                            Text(
                                modifier = Modifier.padding(bottom = 8.dp),
                                text = stringResource(R.string.add_attendee_error_message),
                                style = TaskyTypography.bodySmall.copy(
                                    color = taskyColors.error,
                                )
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = taskyColors.surfaceHigh,
                        unfocusedContainerColor = taskyColors.surfaceHigh,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent,
                        errorIndicatorColor = taskyColors.error
                    ),
                    placeholder = {
                        Text(
                            modifier = Modifier.padding(vertical = 16.dp),
                            text = stringResource(R.string.event_email_hint),
                            style = TaskyTypography.bodyMedium.copy(
                                color = taskyColors.onSurfaceVariant
                            )
                        )
                    }
                )
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
                        .clip(RoundedCornerShape(25.dp)),
                    onClick = onConfirmAdd,
                    enabled = isButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = taskyColors.primary,
                        contentColor = taskyColors.onPrimary,
                        disabledContainerColor = taskyColors.onSurfaceVariant.copy(
                            alpha = 0.7f,
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(36.dp)
                                .alpha(if (isLoading) 1f else 0f),
                            strokeWidth = 1.5.dp,
                            color = taskyColors.onPrimary
                        )
                        Text(
                            modifier = Modifier
                                .alpha(if (isLoading) 0f else 1f),
                            text = stringResource(R.string.add_visitor_button).uppercase(),
                            color = taskyColors.onPrimary,
                            style = TaskyTypography.labelMedium
                        )
                    }
                }


            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun AddAttendeeBottomSheetPreview() {
//    AddAttendeeBottomSheet(
//        userEmail = "william.henry.moody@my-own-personal-domain.com"
//    )
//}
