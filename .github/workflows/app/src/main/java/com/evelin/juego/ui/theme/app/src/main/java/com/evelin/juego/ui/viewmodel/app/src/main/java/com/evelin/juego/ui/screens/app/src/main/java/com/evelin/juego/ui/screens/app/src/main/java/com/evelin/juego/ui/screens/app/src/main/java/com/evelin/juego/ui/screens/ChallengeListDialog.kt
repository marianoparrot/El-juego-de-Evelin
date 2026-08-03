package com.evelin.juego.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.evelin.juego.data.model.Challenge
import com.evelin.juego.ui.theme.BurgundyDark
import com.evelin.juego.ui.theme.BurgundyMid
import com.evelin.juego.ui.theme.RoseGold
import com.evelin.juego.ui.theme.TextWhite
import com.evelin.juego.ui.theme.WineRed

@Composable
fun ChallengeListDialog(
    challenges: List<Challenge>,
    onDismiss: () -> Unit,
    onEdit: (Challenge) -> Unit,
    onDelete: (String) -> Unit,
    onDeleteAll: () -> Unit
) {
    var showDeleteAllConfirm by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(16.dp),
            color = BurgundyDark
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "📋 Mis Retos",
                    style = MaterialTheme.typography.titleLarge,
                    color = RoseGold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (challenges.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No hay retos guardados aún.",
                            color = TextWhite
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(challenges, key = { it.id }) { item ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = BurgundyMid),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.text,
                                        color = TextWhite,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Row {
                                        IconButton(onClick = { onEdit(item) }) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Editar",
                                                tint = RoseGold
                                            )
                                        }
                                        IconButton(onClick = { onDelete(item.id) }) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Eliminar",
                                                tint = WineRed
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (challenges.isNotEmpty()) {
                    Button(
                        onClick = { showDeleteAllConfirm = true },
                        colors = ButtonDefaults.buttonColors(containerColor = WineRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Eliminar todos los retos", color = TextWhite)
                    }
                }

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Cerrar", color = RoseGold)
                }
            }
        }
    }

    if (showDeleteAllConfirm) {
        DeleteAllConfirmDialog(
            onDismiss = { showDeleteAllConfirm = false },
            onConfirm = onDeleteAll
        )
    }
}
