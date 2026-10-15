package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.theme.Historico

class HistoricoViewModel : ViewModel() {

    private val _historicos = mutableStateListOf<Historico>()

    val historicos: List<Historico>
        get() = _historicos

    fun adicionarRotinaConcluida(
        data: String,
        nomeRotina: String
    ) {

        val novoId =
            if (_historicos.isEmpty()) {
                1
            } else {
                _historicos.maxOf { it.idHistorico } + 1
            }

        _historicos.add(
            Historico(
                idHistorico = novoId,
                data = data,
                rotinaConcluida = true,
                nomeRotina = nomeRotina
            )
        )
    }

    fun adicionarMetaConcluida(
        data: String,
        nomeMeta: String
    ) {

        val novoId =
            if (_historicos.isEmpty()) {
                1
            } else {
                _historicos.maxOf { it.idHistorico } + 1
            }

        _historicos.add(
            Historico(
                idHistorico = novoId,
                data = data,
                metaConcluida = true,
                nomeMeta = nomeMeta
            )
        )
    }

    fun removerHistorico(
        historico: Historico
    ) {

        _historicos.removeAll {
            it.idHistorico == historico.idHistorico
        }
    }

    fun limparHistorico() {
        _historicos.clear()
    }
}
