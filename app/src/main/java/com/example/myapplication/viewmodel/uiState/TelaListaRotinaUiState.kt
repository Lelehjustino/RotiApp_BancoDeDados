package com.example.myapplication.viewmodel.uistate

import com.example.myapplication.model.Rotina

data class TelaListaRotinasUiState(
    val rotinas: List<Rotina> = emptyList(),
    val carregando: Boolean = false,
    val rotinaParaExcluir: Rotina? = null
)