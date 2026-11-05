package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Rotina
import com.example.myapplication.model.Atividade
import com.example.myapplication.model.listaRotinas
import com.example.myapplication.model.listaAtividades
import com.example.myapplication.viewmodel.uistate.TelaDesempenhoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DesempenhoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TelaDesempenhoUiState())
    val uiState: StateFlow<TelaDesempenhoUiState> = _uiState.asStateFlow()

    fun atualizarDados() {
        val rotinas = listaRotinas.toList()
        val atividades = listaAtividades.toList()

        val qtdRotinas = rotinas.size
        val qtdAtividades = atividades.size
        val tempoTotal = rotinas.sumOf { it.tempoMinutosRotina }
        val media = if (qtdRotinas > 0) tempoTotal.toFloat() / qtdRotinas else 0f
        val maior = rotinas.maxByOrNull { it.tempoMinutosRotina }

        _uiState.update {
            it.copy(
                rotinas = rotinas,
                quantidadeRotinas = qtdRotinas,
                quantidadeAtividades = qtdAtividades,
                tempoTotal = tempoTotal,
                mediaTempoRotina = media,
                maiorRotina = maior
            )
        }
    }

    fun atividadesDaRotina(idRotina: Int): List<Atividade> {
        return listaAtividades.filter { it.idRotina == idRotina }
    }

    fun percentualDaRotina(rotina: Rotina): Float {
        return 0.5f
    }
}