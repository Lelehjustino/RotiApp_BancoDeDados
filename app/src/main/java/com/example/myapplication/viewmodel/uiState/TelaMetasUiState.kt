package com.example.myapplication.viewmodel.uistate

import com.example.myapplication.model.Meta

data class TelaMetasUiState(
    val metas: List<Meta> = emptyList(),
    val metaSelecionada: Meta? = null,
    val mostrarDialogoNovaMeta: Boolean = false
)