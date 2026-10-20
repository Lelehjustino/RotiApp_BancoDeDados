
package com.example.myapplication.ui.theme

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication.BancoDadosProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "historico")
data class Historico(
    @PrimaryKey
    val idHistorico: Int = 0,
    val data: String,
    val rotinaConcluida: Boolean = false,
    val nomeRotina: String = "",
    val metaConcluida: Boolean = false,
    val nomeMeta: String = ""
)

val listaHistorico = mutableStateListOf<Historico>()

fun proximoIdHistorico(): Int =
    (listaHistorico.maxOfOrNull { it.idHistorico } ?: 0) + 1

fun dataAtual(): String =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        .format(Date())

fun registrarRotinaNoHistorico(nomeRotina: String) {
    val registro = Historico(
        idHistorico = proximoIdHistorico(),
        data = dataAtual(),
        rotinaConcluida = true,
        nomeRotina = nomeRotina
    )
    listaHistorico.add(registro)
    BancoDadosProvider.db.historicoDAO().inserir(registro)
}

fun registrarMetaNoHistorico(nomeMeta: String) {
    val registro = Historico(
        idHistorico = proximoIdHistorico(),
        data = dataAtual(),
        metaConcluida = true,
        nomeMeta = nomeMeta
    )
    listaHistorico.add(registro)
    BancoDadosProvider.db.historicoDAO().inserir(registro)
}

fun removerDoHistorico(historico: Historico) {
    listaHistorico.removeAll { it.idHistorico == historico.idHistorico }
    BancoDadosProvider.db.historicoDAO().excluir(historico)
}
