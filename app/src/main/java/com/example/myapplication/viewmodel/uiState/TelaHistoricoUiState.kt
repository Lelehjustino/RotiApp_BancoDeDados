package com.example.myapplication.viewmodel.uistate

import com.example.myapplication.model.Historico

data class TelaHistoricoUiState(
    val historicos: List<Historico> = emptyList(),
    val totalRotinas: Int = 0,
    val totalMetas: Int = 0
)