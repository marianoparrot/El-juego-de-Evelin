package com.evelin.juego.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.evelin.juego.data.model.Challenge
import com.evelin.juego.ui.theme.BurgundyMid
import com.evelin.juego.ui.theme.RoseGold
import com.evelin.juego.ui.theme.TextWhite
import com.evelin.juego.ui.theme.WineRed

@Composable
fun EditChallengeDialog(
    challenge: Challenge,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(challenge.text) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = BurgundyMid,
        title = { Text("Editar reto", color = TextWhite) },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Reto", color = RoseGold) },
                    singleLine = false,
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RoseGold,
                        unfocusedBorderColor = WineRed,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        onConfirm(text)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = WineRed)
            ) {
                Text("Guardar", color = TextWhite)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = RoseGold)
            }
        }
    )
}
