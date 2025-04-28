package com.example.eduhub.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.eduhub.ui.theme.EduHubTheme

@Composable
fun AlertDialogConfirmation(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        icon = {
            Icon(icon, contentDescription = "Alert Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Logout")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AlertDialogConfirmationPreview() {
    EduHubTheme {
        AlertDialogConfirmation(
            onDismissRequest = {  },
            onConfirmation = {  },
            dialogTitle = "Title",
            dialogText = "Text",
            icon = Icons.Default.Info
        )
    }
}