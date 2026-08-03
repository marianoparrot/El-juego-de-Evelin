package com.evelin.juego.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.evelin.juego.data.model.Challenge
import com.evelin.juego.ui.theme.BorderWine
import com.evelin.juego.ui.theme.BurgundyDark
import com.evelin.juego.ui.theme.BurgundyMid
import com.evelin.juego.ui.theme.CardBackground
import com.evelin.juego.ui.theme.RoseGold
import com.evelin.juego.ui.theme.TextWhite
import com.evelin.juego.ui.theme.WineRed
import com.evelin.juego.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val currentChallenge by viewModel.currentChallenge.collectAsState()
    val challenges by viewModel.challenges.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var showListDialog by remember { mutableStateOf(false) }
    var editingChallenge by remember { mutableStateOf<Challenge?>(null) }

    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BurgundyMid, BurgundyDark, Color.Black)
                )
            )
    ) {
        BackgroundHearts()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "❤️ El juego de Evelin",
                style = MaterialTheme.typography.headlineLarge,
                color = RoseGold,
                textAlign = TextAlign.Center
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .scale(scale.value)
                    .border(1.dp, BorderWine, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentChallenge,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextWhite,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            scale.animateTo(0.92f, animationSpec = tween(100))
                            viewModel.pickRandomChallenge()
                            scale.animateTo(1f, animationSpec = tween(150))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WineRed)
                ) {
                    Text(
                        text = "🎲 Sacar reto",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                OutlinedButton(
                    onClick = { showAddDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RoseGold)
                ) {
                    Text(
                        text = "➕ Añadir reto",
                        style = MaterialTheme.typography.labelLarge,
                        color = RoseGold
                    )
                }

                OutlinedButton(
                    onClick = { showListDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderWine)
                ) {
                    Text(
                        text = "📋 Ver mis retos",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showAddDialog) {
        AddChallengeDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { text -> viewModel.addChallenge(text) }
        )
    }

    if (showListDialog) {
        ChallengeListDialog(
            challenges = challenges,
            onDismiss = { showListDialog = false },
            onEdit = { challenge -> editingChallenge = challenge },
            onDelete = { id -> viewModel.deleteChallenge(id) },
            onDeleteAll = { viewModel.deleteAllChallenges() }
        )
    }

    editingChallenge?.let { challenge ->
        EditChallengeDialog(
            challenge = challenge,
            onDismiss = { editingChallenge = null },
            onConfirm = { newText ->
                viewModel.editChallenge(challenge.id, newText)
                editingChallenge = null
            }
        )
    }
}

@Composable
fun BackgroundHearts() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val heartColor = Color(0x0DFFD1DC)

        val points = listOf(
            Pair(width * 0.15f, height * 0.2f),
            Pair(width * 0.85f, height * 0.3f),
            Pair(width * 0.25f, height * 0.75f),
            Pair(width * 0.75f, height * 0.82f)
        )

        points.forEach { (x, y) ->
            drawHeart(x, y, 40f, heartColor)
        }
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawHeart(
    x: Float,
    y: Float,
    size: Float,
    color: Color
) {
    val path = Path().apply {
        moveTo(x, y + size / 4)
        cubicTo(x, y, x - size / 2, y, x - size / 2, y + size / 4)
        cubicTo(x - size / 2, y + size / 2, x, y + size * 0.75f, x, y + size)
        cubicTo(x, y + size * 0.75f, x + size / 2, y + size / 2, x + size / 2, y + size / 4)
        cubicTo(x + size / 2, y, x, y, x, y + size / 4)
        close()
    }
    drawPath(path = path, color = color)
}
