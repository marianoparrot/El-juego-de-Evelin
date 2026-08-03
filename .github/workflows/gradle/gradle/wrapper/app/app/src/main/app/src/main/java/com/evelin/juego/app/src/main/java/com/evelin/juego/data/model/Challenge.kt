package com.evelin.juego.data.model

import java.util.UUID

data class Challenge(
    val id: String = UUID.randomUUID().toString(),
    val text: String
)
