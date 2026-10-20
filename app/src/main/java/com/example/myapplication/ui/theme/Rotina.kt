
package com.example.myapplication.ui.theme

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.compose.runtime.mutableStateListOf

@Entity(tableName = "rotinas")
data class Rotina(
    @PrimaryKey
    val idRotina: Int,
    var nomeRotina: String,
    var tempoMinutosRotina: Double
)

val listaRotinas = mutableStateListOf<Rotina>()
