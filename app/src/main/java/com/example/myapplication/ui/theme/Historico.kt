
package com.example.myapplication.ui.theme

import androidx.compose.runtime.mutableStateListOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Historico(
    val idHistorico: Int,
    val data: String,

    val rotinaConcluida: Boolean = false,
    val nomeRotina: String = "",

    val metaConcluida: Boolean = false,
    val nomeMeta: String = ""
)

// Lista global e REATIVA
val listaHistorico = mutableStateListOf<Historico>()

fun proximoIdHistorico(): Int {
    return if (listaHistorico.isEmpty()) {
        1
    } else {
        listaHistorico.maxOf { it.idHistorico } + 1
    }
}

fun dataAtual(): String {
    val formato = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale.getDefault()
    )

    return formato.format(Date())
}

fun registrarRotinaNoHistorico(nomeRotina: String) {

    listaHistorico.add(
        Historico(
            idHistorico = proximoIdHistorico(),
            data = dataAtual(),
            rotinaConcluida = true,
            nomeRotina = nomeRotina
        )
    )
}

fun registrarMetaNoHistorico(nomeMeta: String) {

    listaHistorico.add(
        Historico(
            idHistorico = proximoIdHistorico(),
            data = dataAtual(),
            metaConcluida = true,
            nomeMeta = nomeMeta
        )
    )
}

fun removerDoHistorico(historico: Historico) {
    listaHistorico.removeAll {
        it.idHistorico == historico.idHistorico
    }
}

