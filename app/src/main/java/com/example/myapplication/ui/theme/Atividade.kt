
package com.example.myapplication.ui.theme

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.compose.runtime.mutableStateListOf

@Entity(tableName = "atividades")
data class Atividade(
    @PrimaryKey
    val idAtividade: Int,
    val idRotina: Int,
    val nomeAtividade: String,
    val duracaoMinutos: Double
)

val listaAtividades = mutableStateListOf<Atividade>()
fun calcularXp(): Int {
    return listaAtividades.sumOf {
        it.duracaoMinutos.toInt()
    }
}

fun calcularNivel(): Int {
    val xp = calcularXp()
    return (xp / 100) + 1
}

fun calcularXpNoNivel(): Int {
    val xp = calcularXp()
    return xp % 100
}
