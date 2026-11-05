package com.example.myapplication.viewmodel.uistate

import com.example.myapplication.model.Rotina

data class TelaDesempenhoUiState(
    val rotinas: List<Rotina> = emptyList(),
    val nivel: Int = 1,
    val xpNoNivel: Int = 0,
    val progressoNivel: Float = 0f,
    val quantidadeRotinas: Int = 0,
    val quantidadeAtividades: Int = 0,
    val tempoTotal: Int = 0,
    val mediaTempoRotina: Float = 0f,
    val maiorRotina: Rotina? = null
)