package com.example.myapplication.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.compose.runtime.mutableStateListOf

@Entity(tableName = "rotinas")
data class Rotina(
    @PrimaryKey(autoGenerate = true)
    val idRotina: Int = 0,
    val nomeRotina: String,
    val tempoMinutosRotina: Int
)

val listaRotinas = mutableStateListOf<Rotina>()
