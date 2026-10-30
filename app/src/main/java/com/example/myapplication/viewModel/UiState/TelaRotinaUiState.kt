
package com.example.myapplication.viewmodel.uistate

import com.example.myapplication.model.Atividade
import com.example.myapplication.model.Rotina

data class RotinaUiState(
    val rotina: Rotina? = null,
    val atividadesDaRotina: List<Atividade> = emptyList(),
    val atividadesConcluidas: Set<Int> = emptySet(),
    val carregando: Boolean = true,
    val erroCarregamento: Boolean = false
)
