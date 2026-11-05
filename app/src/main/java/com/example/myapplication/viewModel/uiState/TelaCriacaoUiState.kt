package com.example.myapplication.viewmodel.uistate

import com.example.myapplication.model.Atividade

data class TelaCriacaoUiState(
    val nomeRotina: String = "",
    val inicio: String = "",
    val fim: String = "",
    val nomeAtividade: String = "",
    val duracaoAtividade: String = "",
    val atividades: List<Atividade> = emptyList(),
    val idRotinaEditando: Int? = null,
    val carregando: Boolean = false,
    val erro: String? = null
)