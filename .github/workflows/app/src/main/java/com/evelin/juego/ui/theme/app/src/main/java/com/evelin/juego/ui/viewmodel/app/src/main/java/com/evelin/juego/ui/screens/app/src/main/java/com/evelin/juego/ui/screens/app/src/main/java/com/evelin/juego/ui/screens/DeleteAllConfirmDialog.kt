package com.evelin.juego.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.evelin.juego.ui.theme.BurgundyMid
import com.evelin.juego.ui.theme.RoseGold
import com.evelin.juego.ui.theme.TextWhite
import com.evelin.juego.ui.theme.WineRed

@Composable
fun DeleteAllConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = BurgundyMid,
        title = { Text("¿Eliminar todos los retos?", color = TextWhite) },
        text = { Text("Esta acción no se puede deshacer. Se borrarán permanentemente todos los retos guardados.", color = TextWhite) },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = WineRed)
            ) {
                Text("Eliminar", color = TextWhite)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = RoseGold)
            }
        }
    )
}
